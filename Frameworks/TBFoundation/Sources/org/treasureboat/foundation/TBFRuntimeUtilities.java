/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSForwardException;

public class TBFRuntimeUtilities {

  /** 
   * <a href="http://wiki.wocommunity.org/display/documentation/Wonder+Logging">new org.slf4j.Logger</a> 
   */
  static final Logger log = LoggerFactory.getLogger(TBFRuntimeUtilities.class);

  /**
   * Excecutes the specified command line commands. If envp is not null the
   * environment variables are set before executing the command. This method
   * supports timeout's. This is quite important because its -always- possible
   * that a UNIX or WINDOWS process does not return, even with simple shell
   * scripts. This is due to whatever bugs and hence every invocation of
   * <code>Process.waitFor()</code> should be observed and stopped if a
   * certain amount of time is over.
   * 
   * @param command
   *            the commands to execute, this is an String array with two
   *            dimensions the following commands <br>
   *            "ls -la" or "cp /tmp/file1 /tmp/file2" or "open
   *            /Applications/*.app"<br>
   *            would be as String arrays<br>
   * 
   * <pre>
   * new String[] { new String[] { &quot;ls&quot;, &quot;-la&quot; },
   *      new String[] { &quot;cp&quot;, &quot;/tmp/file1&quot;, &quot;/tmp/file2&quot; },
   *      new String[] { &quot;open&quot;, &quot;/Applications/*.app&quot; } }
   * </pre>
   * 
   * @param envp
   *            a <code>String</code> array which represents the environment
   *            variables like
   *            <code>String[] envp = new String[]{"PATH=/usr/bin:/bin", "CVS_RSH=ssh"}</code>,
   *            can be null
   * @param dir
   *            a <code>File</code> object representing the working
   *            directory, can be null
   * 
   * @param timeout
   *            a <code>long</code> which can be either <code>0</code>
   *            indicating this method call waits until the process exits or
   *            any <code>long</code> number larger than <code>0</code>
   *            which means if the process does not exit after
   *            <code>timeout</code> milliseconds then this method throws an
   *            ERXTimeoutException
   * 
   * 
   * @return the results from the processes that were executed
   * 
   * @exception IOException
   *                if something went wrong
   */
  public final static Result execute(String[] command, String[] envp, File dir, long timeout) throws IOException, TimeoutException {
    int exitValue = -1;
    Runtime rt = Runtime.getRuntime();
    Process p = null;
    StreamReader isr = null;
    StreamReader esr = null;
    Result result;
    try {
      if (log.isDebugEnabled()) {
        log.debug("Will execute command {}", new NSArray<String>(command).componentsJoinedByString(" "));
      }
      if (dir == null && envp == null) {
        p = rt.exec(command);

      } else if (dir == null) {
        p = rt.exec(command, envp);

      } else if (envp == null) {
        throw new IllegalArgumentException("if dir != null then envp must also be != null");

      } else {
        p = rt.exec(command, envp, dir);
      }

      // DT: we must read from input and error stream in separate threads
      // because if the buffer from these streams are full the process
      // will block!
      isr = new StreamReader(p.getInputStream());
      esr = new StreamReader(p.getErrorStream());

      if (timeout > 0) {
        TimeoutTimerTask task = new TimeoutTimerTask(p);
        Timer timer = new Timer();
        timer.schedule(task, timeout);
        boolean wasStopped = false;
        try {
          p.waitFor();
          exitValue = p.exitValue();
        } catch (InterruptedException ex) {
          wasStopped = true;
        }
        timer.cancel();
        if (task.didTimeout() || wasStopped) {
          throw new TimeoutException("process didn't exit after " + timeout + " milliseconds");
        }
      } else {
        // wait for the result of the process
        try {
          p.waitFor();
          exitValue = p.exitValue();
        } catch (InterruptedException ex) {
        }
      }
    } finally {
      // Getting stream results before freeing process resources to prevent a case
      // when fast process is destroyed before stream readers read from buffers.
      if (isr != null) {
        if (esr != null) {
          result = new Result(exitValue, isr.getResult(), esr.getResult());
        }
        else {
          result = new Result(exitValue, isr.getResult(), null);
        }
      }
      else if (esr != null) {
        result = new Result(exitValue, null, esr.getResult());
      }
      else {
        result = new Result(exitValue, null, null);
      }

      // Checking exceptions after getting results to ensure that stream readers
      // had already read their buffers by the time of check.
      if (isr != null && isr.getException() != null) {
        log.error("input stream reader got exception,\n      command = {} result = {}", TBFString.toString(command, " "), isr.getResultAsString(), isr.getException());
      }
      if (esr != null && esr.getException() != null) {
        log.error("error stream reader got exception,\n      command = {} result = {}", TBFString.toString(command, " "), esr.getResultAsString(), esr.getException());
      }

      freeProcessResources(p);
    }
    return result;
  }

  /**
   * Frees all of a resources associated with a given process and then
   * destroys it.
   * 
   * @param p process to destroy
   */
  public static void freeProcessResources(Process p) {
    if (p != null) {
      if (p.getInputStream() != null) {
        try {
          p.getInputStream().close();
        } catch (IOException e) {
          // do nothing here
        }
      }

      if (p.getOutputStream() != null) {
        try {
          p.getOutputStream().close();
        } catch (IOException e) {
          // do nothing here
        }
      }

      if (p.getErrorStream() != null) {
        try {
          p.getErrorStream().close();
        } catch (IOException e) {
          // do nothing here
        }
      }

      p.destroy();
    }
  }

  //********************************************************************
  //  Inner classes
  //********************************************************************

  public static class StreamReader {
    private byte[] _result = null;
    private boolean _finished = false;
    private IOException _iox;

    public StreamReader(final InputStream is) {

      Runnable r = new Runnable() {

        @Override
        public void run() {
          ByteArrayOutputStream bout = new ByteArrayOutputStream();
          try {
            int read = -1;
            byte[] buf = new byte[1024 * 50];
            while ((read = is.read(buf)) != -1) {
              bout.write(buf, 0, read);
            }
            _result = bout.toByteArray();
          } catch (IOException e) {
            _iox = e;
            _result =  bout.toByteArray();
          } finally {
            synchronized (StreamReader.this) {
              _finished = true;
              StreamReader.this.notifyAll();
            }
          }
        }

      };
      Thread t = new Thread(r);
      t.start();
    }

    public byte[] getResult() {
      synchronized (this) {
        if(!_finished) {
          try {
            StreamReader.this.wait();
          } catch (InterruptedException e) {
            throw NSForwardException._runtimeExceptionForThrowable(e);
          }
        }
      }
      return _result;
    }

    public boolean isFinished() {
      return _finished;
    }

    public IOException getException() {
      return _iox;
    }

    public String getResultAsString() {
      return getResult() == null ? null : new String(getResult());
    }
  }

  public static class Result {

    private byte[] _response, _error;
    private int _exitValue;

    public Result(int exitValue, byte[] response, byte[] error) {
      _exitValue = exitValue;
      _response = response;
      _error = error;
    }

    public byte[] getResponse() {
      return _response;
    }

    public byte[] getError() {
      return _error;
    }

    public int getExitValue() {
      return _exitValue;
    }

    public String getResponseAsString() {
      return getResponse() == null ? null : new String(getResponse());
    }

    public String getErrorAsString() {
      return getError() == null ? null : new String(getError());
    }
  }

  public static class TimeoutException extends Exception {
    /**
     * Do I need to update serialVersionUID?
     * See section 5.6 <cite>Type Changes Affecting Serialization</cite> on page 51 of the 
     * <a href="http://java.sun.com/j2se/1.4/pdf/serial-spec.pdf">Java Object Serialization Spec</a>
     */
    private static final long serialVersionUID = 1L;

    public TimeoutException(String string) {
      super(string);
    }
  }

  public static class TimeoutTimerTask extends TimerTask {
    private Process _p;
    private boolean _didTimeout = false;

    public TimeoutTimerTask(Process p) {
      _p = p;
    }

    public boolean didTimeout() {
      return _didTimeout;
    }

    @Override
    public void run() {
      try {
        _p.exitValue();
      } catch (IllegalThreadStateException e) {
        _didTimeout = true;
        _p.destroy();
      }
    }
  }

}
