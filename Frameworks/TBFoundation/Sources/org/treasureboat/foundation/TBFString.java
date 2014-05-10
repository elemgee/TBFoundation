/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSForwardException;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;
import com.webobjects.foundation.NSMutableSet;

public class TBFString {

  /** 
   * <a href="http://wiki.wocommunity.org/display/documentation/Wonder+Logging">new org.slf4j.Logger</a> 
   */
  static final Logger log = LoggerFactory.getLogger(TBFString.class);

  //********************************************************************
  //  Methods : メソッド
  //********************************************************************

  /**
   * Simple test if the string is either null or equal to "".
   * 
   * @param s The string to test.
   * 
   * @return The result of the above test.
   */
  public static boolean stringIsNullOrEmpty(String s) {
    return (s == null || s.length() == 0);
  }

  /**
   * Simple utility method that will return null if the string passed in is equal to "";
   * otherwise it will return the passed in string.
   * 
   * @param s The string to test.
   * 
   * @return null If the string is "", otherwise return the string.
   */
  public static String nullForEmptyString(String s) {
    return stringIsNullOrEmpty(s) ? null : s;
  }

  /**
   * Simple utility method that will return the string "" if the string passed in is null;
   * otherwise it will return the passed in string.
   * 
   * @param s The string to test.
   *        
   * 
   * @return The empty string if the string is null, else the string.
   */
  public static String emptyStringForNull(String s) {
    return s == null ? "" : s;
  }

  /**
   * Null-safe wrapper for java.lang.String.trim
   * 
   * @param s The string to trim.
   * 
   * @return trimmed string or null if s was null.
   */
  public static String trimString(String s) {
    if (s == null) {
      return s;
    }
    return s.trim();
  }

  /**
   * Capitalizes a given string. That is, the first character of the returned
   * string will be upper case, and other characters will be unchanged. For
   * example, for the input string "{@code you have a dog}", this method would
   * return "{@code You have a dog}".
   * 
   * @param value to be capitalized.
   * 
   * @return capitalized string
   */
  public static String capitalize(String value) {
    String capital = null;
    if (!stringIsNullOrEmpty(value)) {
      StringBuilder sb = new StringBuilder(value);
      sb.setCharAt(0, Character.toUpperCase(value.charAt(0)));
      capital = sb.toString();            
    }
    return capital != null ? capital : value;
  }

  /**
   * Uncapitalizes a given string.
   * 
   * @param value to be uncapitalized
   * 
   * @return capitalized string
   */
  public static String uncapitalize(String value) {
    String capital = null;
    if (value != null) {
      int length = value.length();
      if (length > 0) {
        StringBuilder buffer = new StringBuilder(value);
        for (int i = 0; i < length; i ++) {
          char ch = value.charAt(i);
          if (i == 0 || i == (length - 1) || (i < (length - 1) && Character.isUpperCase(value.charAt(i + 1)))) {
            buffer.setCharAt(i, Character.toLowerCase(ch));
          } else {
            break;
          }
        }
        capital = buffer.toString();
      }
    }
    return capital != null ? capital : value;
  }

  /** 
   * This method runs about 20 times faster than
   * java.lang.String.toLowerCase (and doesn't waste any storage
   * when the result is equal to the input).  Warning: Don't use
   * this method when your default locale is Turkey.
   * java.lang.String.toLowerCase is slow because (a) it uses a
   * StringBuffer (which has synchronized methods), (b) it
   * initializes the StringBuffer to the default size, and (c) it
   * gets the default locale every time to test for name equal to
   * "tr".
   * @see <a href="http://www.norvig.com/java-iaq.html#tolower">tolower</a> 
   * 
   * @author Peter Norvig 
   */
  public static String toLowerCase(String str) {
    if (str == null) {
      return null;
    }

    int len = str.length();
    int different = -1;
    // See if there is a char that is different in lowercase.
    for(int i = len-1; i >= 0; i--) {
      char ch = str.charAt(i);
      if (Character.toLowerCase(ch) != ch) {
        different = i;
        break;
      }
    }

    // If the string has no different char, then return the string as is,
    // otherwise create a lowercase version in a char array.
    if (different == -1) {
      return str;
    }
    char[] chars = new char[len];
    str.getChars(0, len, chars, 0);
    // (Note we start at different, not at len.)
    for(int j = different; j >= 0; j--) {
      chars[j] = Character.toLowerCase(chars[j]);
    }
    return new String(chars);
  }

  /**
   * Returns a String by invoking toString() on each object from the array. After each toString() call
   * the separator is appended to the buffer.
   * 
   * @param array an object array from which to get a nice String representation
   * @param separator a separator which is displayed between the objects toString() value
   *
   * @return a string representation from the array
   */
  public static String toString(Object[] array, String separator) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < array.length; i++) {
      Object o = array[i];
      sb.append(o.toString());
      sb.append(separator);
    }
    return sb.toString();
  }

  /**
   * String multiplication.
   * 
   * @param n The number of times to concatenate a given string.
   * @param s The string to be multiplied.
   * 
   * @return multiplied string
   */
  public static String stringWithNtimesString(int n, String s) {
    StringBuilder sb = new StringBuilder(n * (s != null ? s.length() : "null".length()));
    for (int i=0; i<n; i++) {
      sb.append(s);
    }
    return sb.toString();
  }

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 「最初 or 最後」に文字列を追加。
   * "(", "[", "{" の閉じる括弧処理あり
   * 
   * Enclose the value with matching enclose characters of "(", "[", "{"
   *
   * @param encloseChar
   *        追加文字列
   * @param value
   *        処理対象の文字列
   * 
   * @return 
   *        処理結果
   */
  public static String enclose(char encloseChar, String value) {
    if(stringIsNullOrEmpty(value)) {
      return "";
    }

    StringBuilder sb = new StringBuilder(value.length() + 2);
    sb.append(encloseChar);
    sb.append(value);

    if(encloseChar == '(') {
      sb.append(')');
    } else if(encloseChar == '[') {
      sb.append(']');
    } else if(encloseChar == '{') {
      sb.append('}');
    } else {
      sb.append(encloseChar);
    }
    return sb.toString();
  }

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 文字列の最後の指定文字を削除
   *
   * @param str
   *        処理文字列
   * @param deleteStr
   *        削除文字列
   * 
   * @return
   *        結果文字列
   */
  public static String deleteTailFromString(String str, String deleteStr){
    if(stringIsNullOrEmpty(str) || stringIsNullOrEmpty(deleteStr)) {
      return str;
    }

    int delLen = deleteStr.length();
    if(str.length() >= deleteStr.length()) {
      if(str.lastIndexOf(deleteStr) == (str.length() - delLen)) {
        str = str.substring(0, str.length() - delLen);
      }
    }
    return str;
  }  

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 指定文字列を選択
   * false の場合には selectChars 文字以外を残す、true の場合には selectChars 文字を残す
   *
   * @param selectChars
   *        処理対象の文字-配列
   * @param str
   *        処理対象の文字列
   * @param flag
   *        <code>false</code> の場合には selectChars 文字以外を残す、<code>true</code> の場合には selectChars 文字を残す
   * 
   * @return 
   *        処理済みの文字列
   * </span>
   */
  public static String selectsChars(char[] selectChars, String str, boolean flag){
    if(stringIsNullOrEmpty(str) || selectChars == null || selectChars.length == 0) {
      return str;
    }

    int loopIdxMax =  str.length();
    int charLoopIdxMax = selectChars.length;
    StringBuilder sb = new StringBuilder(loopIdxMax);
    char targetChar;
    boolean addFlg = true;
    for(int loopIdx = 0; loopIdx < loopIdxMax; loopIdx++){
      targetChar = str.charAt(loopIdx);
      addFlg = !flag;
      for(int charloopIdx = 0; charloopIdx < charLoopIdxMax; charloopIdx++){
        if(targetChar == selectChars[charloopIdx]){
          addFlg = flag;
          break;
        }
      }
      if(addFlg) {
        sb.append(targetChar);
      }
    }    
    return emptyStringForNull(sb.toString());
  }

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 文字列より数値のみを残します。
   * 
   * @param string
   *        文字列
   * 
   * @return 
   *        数値のみの文字列
   */
  public static String digitString(String string) {
    return TBFString.selectsChars(TBFConstants.NUMBER_CHARS, string, true);
  }

  public static int digitStringAsInt(String string) {
    return TBFValueUtilities.intValue(digitString(string));
  }

  public static int digitStringAsIntValueWithDefault(String string, int defaultValue) {
    return TBFValueUtilities.intValueWithDefault(digitString(string), defaultValue);
  }

  /**
   * Removes any character which is in characters from the source string.
   * 
   * @param source The string which will be modified.
   * @param characters The characters that are not allowed to be in the source.
   * 
   * @return A new string without any characters from the characters argument.
   */
  public static String removeCharacters(String source, String characters) {
    StringBuilder sb = new StringBuilder();
    int l = source.length();
    for (int i = 0; i < l; i++) {
      char c = source.charAt(i);
      if (characters.indexOf(c) == -1) {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * Remove all half/full-byte space chars from the requestString
   * 
   * <hr />
   * 
   * 半角/全角スペースを削除。
   * 
   * @param requestString
   *    The string where the spaces get removed
   *    処理対象の文字列
   * 
   * @return
   *    result string
   *    処理済みの文字列
   */
  public static String removeSpaces(String requestString){
    //  NSArray<String> parts = NSArray.componentsSeparatedByString(aString, " ");
    //  return parts.componentsJoinedByString("");
    return selectsChars(TBFConstants._SPACE_CHARS, requestString, false);
  }

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 指定文字列以外を削除。
   *
   * @param notDeleteChars
   *        処理対象の文字-配列
   * @param str
   *        処理対象の文字列
   * 
   * @return
   *        処理済みの文字列
   */
  public static String filterChars(char[] notDeleteChars, String str) {
    return TBFString.selectsChars(notDeleteChars, str, true);
  }

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 指定文字の削除。
   *
   * @param deleteChars
   *        処理対象の文字-配列
   * @param str
   *        処理対象の文字列
   * 
   * @return
   *        処理済みの文字列
   */
  public static String deleteChars(char[] deleteChars, String str) { 
    return TBFString.selectsChars(deleteChars, str, false);
  }

  /**
   * TODO english doc
   * 
   * <hr />
   * 
   * 2桁数字の 0 追加
   * 1桁なら「0」を追加。
   * null 空文字なら「00」を返す。
   *
   * @param str
   *        処理対象の数字文字列
   * 
   * @return 
   *        結果文字列
   */
  public static String stringForTwoDigitFormat(String str) {
    if(stringIsNullOrEmpty(str)) {
      return "00";
    }

    if(str.length() == 1) {
      return ("0" + str);  
    }

    return str;
  }

  public static String stringForTwoDigitFormat(int i) {
    return stringForTwoDigitFormat(String.valueOf(i));
  }

  /**
   * Pads a string to the specified number of chars by adding the given pad char on the right side.  If the
   * string is longer than paddedLength, it is returned unchanged.
   *
   * @param string The string to pad.
   * @param padChar The character to pad with.
   * @param paddedLength The length to pad to.
   * 
   * @return the padded string.
   */
  public static String rightPad(String string, char padChar, int paddedLength) {
    if (string.length() >= paddedLength) {
      return string;
    }

    StringBuilder sb = new StringBuilder(string);
    for (int i = string.length(); i < paddedLength; i++) {
      sb.append(padChar);
    }
    return sb.toString();
  }

  /**
   * Pads a string to the specified number of chars by adding the the given pad char on the left side.  If the
   * string is longer than paddedLength, it is returned unchanged.
   *
   * @param string The string to pad.
   * @param padChar The character to pad with.
   * @param paddedLength The length to pad to.
   * 
   * @return the padded string.
   */
  public static String leftPad(String string, char padChar, int paddedLength) {
    if (string.length() >= paddedLength) {
      return string;
    }

    StringBuilder sb = new StringBuilder();
    for (int i = string.length(); i < paddedLength; i++) {
      sb.append(padChar);
    }
    sb.append(string);
    return sb.toString();
  }

  /** 
   * Locate the the first numeric character in the given string.
   * 
   * @param str The string to scan.
   * 
   * @return position in string or -1 if no numeric found.
   */ 
  public static int indexOfNumericInString(String str) {
    return indexOfNumericInString(str, 0);
  }

  /** 
   * Locate the the first numeric character 
   * after <code>fromIndex</code> in the given string.
   * 
   * @param str The string to scan.
   *        
   * @param fromIndex The index position from which to start.
   *        
   * @return position in string or -1 if no numeric found
   */ 
  public static int indexOfNumericInString(String str, int fromIndex) {
    if (str == null) {
      throw new IllegalArgumentException("String cannot be null.");
    }

    int pos = -1;
    for (int i = fromIndex; i < str.length(); i++) {
      char c = str.charAt(i);
      if ('0' <= c  &&  c <= '9') {
        pos = i;
        break;
      }
    }
    return pos;
  }

  /**
   * Escapes the given PCDATA string as CDATA.
   * 
   * @param pcdata The string to escape.
   *        
   * @return The escaped string.
   */
  public static String escapePCData(String pcdata) {
    if(pcdata == null) { 
      return null; 
    }

    int start = 0;
    int end = 0;
    String close = "]]>";
    String escape = "]]]]><![CDATA[>";

    StringBuilder sb = new StringBuilder("<![CDATA[");

    do {
      end = pcdata.indexOf(close, start);
      sb.append(pcdata.substring(start, (end == -1 ? pcdata.length() : end)));
      if(end != -1) { 
        sb.append(escape); 
      }
      start = end;
      start += 3;
    } while (end != -1);

    sb.append(close);

    return sb.toString();
  }

  public static String escapeNonBasicLatinChars(char c) {
    Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
    if (block != null  &&  Character.UnicodeBlock.BASIC_LATIN.equals(block)) {
      return String.valueOf(c);
    }
    return toHexString(c);
  }

  public static String escapeNonBasicLatinChars(String str) {
    if (str == null) {
      return null;
    }

    StringBuilder result = new StringBuilder(str.length());
    for (int i = 0; i < str.length(); i++)  {
      result.append(escapeNonBasicLatinChars(str.charAt(i)));
    }

    return result.toString();
  }

  /**
   * Escapes the apostrophes in a Javascript string with a backslash.
   * 
   * @param sourceString The source string to escape.
   *        
   *        
   * @return  The escaped javascript string.
   *       
   */
  public static String escapeJavascriptApostrophes(String sourceString) {
    return TBFString.escape(new char[] { '\'' }, '\\', sourceString);
  }

  /**
   * Escapes the given characters with the given escape character in _sourceString.  This 
   * implementation is specifically designed for large strings.  In the event that no characters 
   * are escaped, the original string will be returned with no new object creation.  A null
   * _sourceString will return null.
   * 
   * Example: sourceString = Mike's, escape chars = ', escape with = \, returns Mike\'s
   * 
   * @param 
   *        _escapeChars The list of characters to escape.
   * @param 
   *        _escapeWith The escape character to use.
   * @param 
   *        _sourceString The string to escape the characters in.
   * 
   * @return 
   *        The escaped string.
   */
  public static String escape(char[] _escapeChars, char _escapeWith, String _sourceString) {
    String targetString;
    if (_sourceString == null) {
      targetString = null;
    } else {
      StringBuilder targetBuffer = null;
      int lastMatch = 0;
      int length = _sourceString.length();
      for (int sourceIndex = 0; sourceIndex < length; sourceIndex++) {
        char ch = _sourceString.charAt(sourceIndex);
        boolean escape = false;
        for (int escapeNum = 0; !escape && escapeNum < _escapeChars.length; escapeNum++) {
          if (ch == _escapeChars[escapeNum]) {
            escape = true;
          }
        }
        if (escape) {
          if (targetBuffer == null) {
            targetBuffer = new StringBuilder(length + 100);
          }
          if (sourceIndex - lastMatch > 0) {
            targetBuffer.append(_sourceString.substring(lastMatch, sourceIndex));
          }
          targetBuffer.append(_escapeWith);
          lastMatch = sourceIndex;
        }
      }
      if (targetBuffer == null) {
        targetString = _sourceString;
      } else {
        targetBuffer.append(_sourceString.substring(lastMatch, length));
        targetString = targetBuffer.toString();
      }
    }
    return targetString;
  }

  public static String toHexString(char c) {
    StringBuilder result = new StringBuilder("\u005C\u005Cu9999".length());
    String u = Long.toHexString(c).toUpperCase();
    switch (u.length()) {
    case 1:
      result.append("\u005C\u005Cu000");
      break;

    case 2:
      result.append("\u005C\u005Cu00");
      break;

    case 3:
      result.append("\u005C\u005Cu0");
      break;

    default:
      result.append("\u005C\u005Cu");
      break;
    }
    result.append(u);
    return result.toString();
  }

  public static String toHexString(String str) {
    if (str == null) {
      return null;
    }

    StringBuilder result = new StringBuilder("\u005C\u005Cu9999".length() * str.length());
    for (int i = 0; i < str.length(); i++) {
      result.append(toHexString(str.charAt(i)));
    }
    return result.toString();
  }

  /**
   * Converts a byte array to hex string.
   * 
   * @param block The byte array.
   *          
   * @return The hex string.
   */
  public static String byteArrayToHexString(byte[] block) {
    int len = block.length;
    StringBuilder buf = new StringBuilder(2 * len);
    for (int i = 0; i < len; ++i) {
      int high = ((block[i] & 0xf0) >> 4);
      int low = (block[i] & 0x0f);
      buf.append(TBFConstants.HEX_CHARS[high]);
      buf.append(TBFConstants.HEX_CHARS[low]);
    }
    return buf.toString();
  }

  /**
   * Converts a even-length, hex-encoded String to a byte array.
   * 
   * @param hexString The hex string to convert.
   *        
   * @return The byte array of given hex string.
   */
  public static byte[] hexStringToByteArray(String hexString) {
    int length = hexString.length();
    if(length % 2 == 1) {
      throw new IllegalArgumentException("String must have even length: " + length);
    }
    byte array[] = new byte[length/2];

    for(int i = 0; i < array.length; i++) {
      char c1 = hexString.charAt(i*2);
      char c2 = hexString.charAt(i*2+1);
      byte b = 0;

      if(c1 >= '0' && c1 <= '9') {
        b += (c1 - 48) * 16;
      } else if(c1 >= 'a' && c1 <= 'f') {
        b += ((c1 - 97) + 10) * 16;
      } else if(c1 >= 'A' && c1 <= 'F') {
        b += ((c1 - 65) + 10) * 16;
      } else {
        throw new IllegalArgumentException("Illegal Character: '" + c1 + "' in " + hexString);
      }

      if(c2 >= '0' && c2 <= '9') {
        b += c2 - 48;
      } else if(c2 >= 'a' && c2 <= 'f') {
        b += (c2 - 97) + 10;
      } else if(c2 >= 'A' && c2 <= 'F') {
        b += (c2 - 65) + 10;
      } else {
        throw new IllegalArgumentException("Illegal Character: '" + c2 + "' in " + hexString);
      }

      array[i] = b;
    }
    return array;
  }

  /**
   * Utility to convert from UTF-8 bytes without the try/catch. 
   * Throws an NSForwardException in the unlikely case that your encoding can't be found.
   * 
   * @param bytes The string to convert.
   */
  public static String fromUTF8Bytes(byte bytes[]) {
    return fromBytes(bytes, CharEncoding.UTF_8);
  }

  /**
   * Utility to convert from bytes without the try/catch.
   * Throws an NSForwardException in the unlikely case that your encoding can't be found.
   * 
   * @param bytes The string to convert.
   * 
   * @param encoding
   */
  public static String fromBytes(byte bytes[], String encoding) {
    if(bytes == null) {
      return null;
    }
    try {
      return new String(bytes, encoding);
    }
    catch (UnsupportedEncodingException e) {
      throw NSForwardException._runtimeExceptionForThrowable(e);
    }
  }

  /**
   * Utility to convert to UTF-8 bytes without the try/catch.
   * Throws an NSForwardException in the unlikely case that your encoding can't be found.
   * 
   * @param string The string to convert.
   */
  public static byte[] toUTF8Bytes(String string) {
    return toBytes(string, CharEncoding.UTF_8);
  }

  /**
   * Utility to convert to bytes without the try/catch. 
   * Throws an NSForwardException in the unlikely case that your encoding can't be found.
   * 
   * @param string The string to convert.
   * @param encoding
   */
  public static byte[] toBytes(String string, String encoding) {
    if(string == null) {
      return null;
    }
    try {
      return string.getBytes(encoding);
    }
    catch (UnsupportedEncodingException e) {
      throw NSForwardException._runtimeExceptionForThrowable(e);
    }
  }

  public static final String concat(String s1, String s2) {
    return s1.concat(s2);
  }

  public static final String concat(String s1, String s2, String s3) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length());
    return sb.append(s1).append(s2).append(s3).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length());
    return sb.append(s1).append(s2).append(s3).append(s4).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4, String s5) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length() + s5.length());
    return sb.append(s1).append(s2).append(s3).append(s4).append(s5).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4, String s5, String s6) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length() + s5.length() + s6.length());
    return sb.append(s1).append(s2).append(s3).append(s4).append(s5).append(s6).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length() + s5.length() + s6.length() + s7.length());
    return sb.append(s1).append(s2).append(s3).append(s4).append(s5).append(s6).append(s7).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length() + s5.length() + s6.length() + s7.length() + s8.length());
    return sb.append(s1).append(s2).append(s3).append(s4).append(s5).append(s6).append(s7).append(s8).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length() + s5.length() + s6.length() + s7.length() + s8.length() + s9.length());
    return sb.append(s1).append(s2).append(s3).append(s4).append(s5).append(s6).append(s7).append(s8).append(s9).toString();
  }

  public static final String concat(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10) {
    StringBuilder sb = new StringBuilder(s1.length() + s2.length() + s3.length() + s4.length() + s5.length() + s6.length() + s7.length() + s8.length() + s9.length() + s10.length());
    return sb.append(s1).append(s2).append(s3).append(s4).append(s5).append(s6).append(s7).append(s8).append(s9).append(s10).toString();
  }

  /**
   * Returns a string from the contents of the given URL using UTF-8.
   * 
   * @param url The URL to read from.
   * 
   * @return The string that was read.
   * 
   * @throws IOException if the connection fails.
   */
  public static String stringFromURL(URL url) throws IOException {
    return TBFString.stringFromURL(url, CharEncoding.UTF_8);
  }

  /**
   * Returns a string from the contents of the given URL.
   * 
   * @param url The URL to read from.
   * @param encoding The string encoding to read with.
   *
   * @return the string that was read.
   * 
   * @throws IOException if the connection fails.
   */
  public static String stringFromURL(URL url, String encoding) throws IOException {
    InputStream is = url.openStream();
    try {
      return TBFString.stringFromInputStream(is, encoding);
    }
    finally {
      is.close();
    }
  }

  /**
   * Returns a string from the input stream using the UTF-8 encoding.
   * 
   * @param in The stream to read.
   * 
   * @return string representation of the stream.
   * 
   * @throws IOException if things go wrong.
   */
  public static String stringFromInputStream(InputStream in) throws IOException {
    return TBFString.stringFromInputStream(in, CharEncoding.UTF_8);
  }

  /**
   * Returns a string from the input stream.
   * 
   * @param in The stream to read.
   * @param encoding to be used, null will use the default.
   * 
   * @return string representation of the stream.
   * 
   * @throws IOException if things go wrong.
   */
  public static String stringFromInputStream(InputStream in, String encoding) throws IOException {
    return new String(TBFFileUtilities.bytesFromInputStream(in), encoding);
  }

  /**
   * Try to refactor a string for randomization of a final String.
   * Sample : "[Yuki,Alice,Ato] is going to the store at [7-11] o'clock."
   * 
   * @param string - A string for randomization.
   * @param randomizer - Use for randomization.
   * 
   * @return the final string.
   */
  public static String randomizedString(String string, long randomizer) {
    if (TBFString.stringIsNullOrEmpty(string)) {
      return null;
    }

    int open = 0;
    int close = 0;
    int len = string.length();

    StringBuilder sb = new StringBuilder(len);
    StringBuilder sb2 = new StringBuilder();

    boolean weAreOpen = false;
    boolean fromTo = false;
    for(int i = 0; i < len; i++) {
      char ch = string.charAt(i);

      if ('[' == ch) {
        weAreOpen = true;
        open++;
      } 

      if(weAreOpen) {
        if (('[' != ch) && (']' != ch)) {
          sb2.append(ch);
        }
        if ('-' == ch) {
          fromTo = true;
        }
      } else {
        sb.append(ch);
      }

      if (']' == ch) {
        close++;
        if(fromTo) {
          NSArray<String> array = NSArray.componentsSeparatedByString(sb2.toString(), "-");
          if(array.count() != 2) {
            sb.append(sb2.toString());
          } else {
            int start = TBFValueUtilities.intValue(array.objectAtIndex(0));
            int end = TBFValueUtilities.intValue(array.objectAtIndex(1));
            int random = start + TBFValueUtilities.intValue(randomizer % (end - start));
            sb.append(random);
          }
        } else {
          NSArray<String> array = NSArray.componentsSeparatedByString(sb2.toString(), TBFConstants.COMMA);
          if(array.count() == 0) {
            sb.append(sb2.toString());
          } else {
            int random = TBFValueUtilities.intValue(randomizer % array.count());
            sb.append(array.objectAtIndex(random));
          }
        }
        sb2 = new StringBuilder();
        weAreOpen = false;
        fromTo = false;
      }      
    }

    if(open == 0 && close == 0) {
      return string;
    }

    if(open != close) {
      log.warn("message [ & ] are not equal. : {}", string);
      return string;      
    }

    return sb.toString();
  }

  /**
   * Takes a String and, if escaping is required, returns a new String with
   * certain characters escaped out. If escaping is not required, no conversion
   * is performed and the original string is returned. This method is used to
   * escape strings which will appear in the <em>visible</em> part of an HTML
   * file (that is, not inside a tag). The escaped characters are: "&lt;",
   * "&gt;", "&", and double quote.
   * 
   * @param value The String on which conversion is to be performed.
   *        
   *        
   * @return  a new String with certain characters escaped out or if escaping is
   *          not required, returns the original String.
   *       
   */
  public static String stringByEscapingHTMLString(String value) {
    String escapedString = value;
    if (!stringIsNullOrEmpty(value)) {
      if (requiresHTMLEscaping(value, TBFConstants.HTMLStringReservedCharacters)) {
        escapedString = _stringByEscapingString(value, TBFConstants.HTMLStringReservedCharacters, TBFConstants.HTMLStringReservedEscapeSequences);
      }
    }
    return escapedString;
  }

  /**
   * This protected class method takes a String and an array of characters and
   * returns <code>true</code> if any of the characters in the character array
   * are found in the String.
   * 
   * @param aString The input string whose characters are to checked.
   *          
   * @param charactersString The input array of characters.
   *          
   *          
   * @return <code>true</code> if any of the characters in the character array
   *         are found in the String, <code>false</code> otherwise.
   */
  private static boolean requiresHTMLEscaping(String aString, char[] charactersString) {
    boolean doesRequireConversion = false;
    if (aString != null) {
      int length = aString.length();
      for (int i = 0; i < length && !doesRequireConversion; i++) {
        char c = aString.charAt(i);
        doesRequireConversion = c < 128 && TBFConstants.map[c];
      }
    }
    return doesRequireConversion;
  }

  private static String _stringByEscapingString(String value, char[] charactersString, String[] escapeSequences) {
    StringBuilder modifiedString = null;
    if (value != null) {
      int stringLength = value.length();
      int clength = charactersString.length;
      modifiedString = new StringBuilder(stringLength);
      for (int i = 0; i < stringLength; i++) {
        char c = value.charAt(i);
        int index = 0;
        while (index < clength && c != charactersString[index]) {
          index++;
        }
        if (index < clength) {
          modifiedString.append(escapeSequences[index]);
        } else {
          modifiedString.append(c);
        }
      }
    }
    return modifiedString.toString();
  }

  //********************************************************************
  //  initWithTemplate
  //********************************************************************

  /**
   * Cover method for calling the four argument method
   * passing in <code>null</code> for the <code>otherObject</code>
   * parameter. See that method for documentation.
   * 
   * <hr />
   * 
   * 指定のテンプレート文字列をパースします。
   * 
   * @param template
   *        Template to use to parse.
   *        パースするテンプレート文字列
   * @param object
   *        To resolve keys.
   *        キーを決定するオブジェクト
   * @param defaultValueObject
   *        The object used to resolve default keys.
   *        デフォルト・キーを決定するオブジェクト
   * 
   * @return 
   *        The parsed template with keys replaced.
   *        キーで置き換えたパース済みテンプレート文字列
   */
  public static String initWithTemplate(String template, Object object, Object defaultValueObject) {
    if(stringIsNullOrEmpty(template)) {
      return template;
    }

    if (template.indexOf(DEFAULT_DELIMITER) == TBFConstants.NOT_FOUND) {
      return template;
    }

    String convertedValue = template;
    String lastConvertedValue = null;
    while (convertedValue != lastConvertedValue && convertedValue.indexOf(DEFAULT_DELIMITER) > TBFConstants.NOT_FOUND) {
      lastConvertedValue = convertedValue;
      convertedValue = initWithTemplate(convertedValue, DEFAULT_DELIMITER, DEFAULT_UNDEFINED_KEY_LABEL, object, defaultValueObject);
    }

    if (convertedValue.indexOf(DEFAULT_UNDEFINED_KEY_LABEL) > TBFConstants.NOT_FOUND) {
      log.warn("following format \"{}\" has missing keys. keylist : '{}'", convertedValue, keysInTemplate(template, DEFAULT_DELIMITER));
    }
    return convertedValue;
  }

  /**
   * This method replaces the keys enclosed between the
   * delimiter with the values found in object and otherObject.
   * It first looks for a value in object, and then in otherObject
   * if the key is not found in object. Therefore, otherObject is
   * a good place to store default values while object is a
   * good place to override default values. 
   * <p>
   * When the value is not found in both object and otherObject, 
   * it will replace the key with the undefined key label which 
   * defaults to "==?==". Note that a <code>null</code> 
   * result will also output the label, so you might want to have the empty
   * string as the undefined key label.
   * 
   * 
   * <hr />
   * 
   * このメソッドは句切り文字で囲んでいるキーをobjectやdefaultValueObjectで置き換えます。
   * 最初はobject内でキーを検索し、見つからなければotherObject内で検索を行います。
   * defaultValueObjectはデフォルト値を設定する最適な場所です。
   * 
   * objectとdefaultValueObjectの両方にキーが見つからなければ、undefined key label（デフォルト "==?=="）で置き換えます。
   * デフォルト・ラベルはコンストラクタで指定が可能です。
   * 
   * 注意：キーがnullの場合でも、デフォルト・ラベルが使用されるので、デフォルト・ラベルを空な文字列 "" にするといい。
   * 
   * 
   * @param template
   *        To use to parse.
   *        パースするテンプレート
   * @param delimiter
   *        To use to check for keys.
   *        キー検索する句切り文字
   * @param undefinedLabel
   *        
   *        見つからない場合に使用されるラベル
   * @param object
   *        To resolve keys from.
   *        キーを決定するオブジェクト
   * @param defaultValueObject
   *        The object used to resolve default keys.
   *        デフォルト・キーを決定するオブジェクト
   * 
   * @return 
   *        Parsed template with keys replaced.
   *        キーで置き換えたパース済みテンプレート
   */
  public static String initWithTemplate(String template, String delimiter, String undefinedLabel, Object object, Object defaultValueObject) {
    if (template == null) {
      throw new IllegalArgumentException("Attempting to parse null template!");
    }
    if (object == null) {
      throw new IllegalArgumentException("Attempting to parse template with null object!");
    }
    if (delimiter == null) {
      delimiter = DEFAULT_DELIMITER;
    }
    if (undefinedLabel == null) {
      undefinedLabel = DEFAULT_UNDEFINED_KEY_LABEL;
    }

    if (!isLoggingDisabled && log.isDebugEnabled()) {
      log.debug("Parsing template: {} with delimiter: {} object: {}" + template, delimiter, object);
      log.debug("otherObject: {}", defaultValueObject);
    }

    NSArray<String> components = NSArray.componentsSeparatedByString(template, delimiter);
    if (!isLoggingDisabled && log.isDebugEnabled()) {
      log.debug("Components: {}", components);
    }

    boolean deriveElement = false; // If the template starts with delim, the first component will be a zero-length string.
    StringBuilder sb = new StringBuilder();
    Object objects[];
    if (defaultValueObject != null) {
      objects = new Object[] {object, defaultValueObject};
    } else {
      objects = new Object[] {object};
    }

    for (Enumeration<String> e = components.objectEnumerator(); e.hasMoreElements();) {
      String element = e.nextElement();
      if(!isLoggingDisabled) {
        log.debug("Processing Element: {}", element);
      }

      if(deriveElement) {
        if(!isLoggingDisabled) {
          log.debug("Deriving value ...");
        }
        if(element.length() == 0) {
          throw new IllegalArgumentException("\"\" is not a valid keypath in template: " + template);
        }

        Object result = undefinedLabel;
        for (int i = 0; i < objects.length; i++) {
          Object o = objects[i];

          if(o != null && result == undefinedLabel) {
            try {
              if(!isLoggingDisabled && log.isDebugEnabled()) {
                log.debug("calling objectForObjectAndKey({}, {})", o, element);
              }

              result = objectForObjectAndKey(o, element);
              // Just in case the above doesn't throw an exception when the 
              // key is not defined. (NSDictionary doesn't seem to throw the exception.)
              if(result == null) {
                result = undefinedLabel;
              }
            } catch (NSKeyValueCoding.UnknownKeyException t) {
              result = undefinedLabel;
            } catch (Throwable t) {
              throw new NSForwardException(t, "An exception occurred while parsing element, "
                  + element + ", of template, \""
                  + template + "\": "
                  + t.getMessage());
            }
          }
        }
        if(result == undefinedLabel) {
          if (!isLoggingDisabled && log.isDebugEnabled()) {
            log.debug("Could not find a value for '{}' of template, '{}' in either the object or extra data.", element, template);
          }
        }
        sb.append(result.toString());
        deriveElement = false;
      } else {
        if(element.length() > 0) {
          sb.append(element);
        }
        deriveElement = true;
      }
      if(!isLoggingDisabled && log.isDebugEnabled()) {
        log.debug("Buffer: {}", sb);
      }
    }
    return sb.toString();
  }

  /**
   * Calculates the set of keys used in a given template
   * for a given delimiter.
   * 
   * <hr />
   * 
   * 指定するテンプレート内の指定する句切り文字を使ってキーを計算します。
   *  
   * @param template
   *      to check for keys
   *      検証するテンプレート
   * @param delimiter
   *      for finding keys
   *      句切り文字
   * 
   * @return
   *      array of keys
   *      キーの配列
   */
  public static NSArray<String> keysInTemplate(String template, String delimiter) {
    NSMutableSet<String> keys = new NSMutableSet<String>();
    if (delimiter == null) {
      delimiter = DEFAULT_DELIMITER;
    }

    NSArray<String> components = NSArray.componentsSeparatedByString(template, delimiter);
    if (!isLoggingDisabled  &&  log.isDebugEnabled()) {
      log.debug("Components: " + components);
    }

    boolean deriveElement = false; // If the template starts with delim, the first component will be a zero-length string.
    for (Enumeration<String> e = components.objectEnumerator(); e.hasMoreElements();) {
      String element = e.nextElement();
      if (deriveElement) {
        if (element.length() == 0) {
          throw new IllegalArgumentException("\"\" is not a valid keypath");
        }
        keys.addObject(element);
        deriveElement = false;
      } else {
        deriveElement = true;
      }
    }
    return keys.allObjects();
  }    

  /** 
   * Flag to disable logging. 
   * for its internal parser object in order to prevent an infinite debug logging loop. 
   *
   * true として自動的にセットされます。そうしないと永遠ループになるのです。 
   */
  private static boolean isLoggingDisabled = false;

  /** 
   * The default label for keys not found while parsing
   * パス中にキーを見つからない場合のデフォルト・ラベル 
   */
  private static final String DEFAULT_UNDEFINED_KEY_LABEL = "?";

  /** 
   * The default delimiter
   * デフォルト句切り文字 
   */
  private static final String DEFAULT_DELIMITER = "@@";

  /**
   * To allow flexibility of the variable provider object type we use similar
   * logic to NSDictionary valueForKeyPath. Consequently
   * <code>java.util.Properties</code> objects that use keyPath separator (.)
   * in the property names (which is common) can be reliably used as object
   * providers.
   * 
   * @param anObject
   * @param aKeyPath
   * 
   * @return the value corresponding to either a key with value
   *         <code>aKeypath</code>, or when no key, a keyPath with value
   *         <code>aKeyPath</code>
   */
  private static Object objectForObjectAndKey(Object anObject, String aKeyPath) {
    Object result = null;

    boolean isKeyPath = (aKeyPath.indexOf(NSKeyValueCodingAdditions.KeyPathSeparator) > TBFConstants.NOT_FOUND);
    if(!isKeyPath) {
      // Mimic NSDictionary valueForKeypath behavior which first checks for a
      // "flattened" key before calling real valueForKeypath logic.
      try {
        result = NSKeyValueCoding.Utility.valueForKey(anObject, aKeyPath);
      }
      catch (NSKeyValueCoding.UnknownKeyException t) {
      }
    }

    if (result == null) {
      return NSKeyValueCodingAdditions.Utility.valueForKeyPath(anObject, aKeyPath);
    }
    return result;
  }

  //********************************************************************
  //  initWithFormat
  //********************************************************************

  /**
   * @param format
   * @param arguments
   * 
   * @return
   */
  public static String initWithFormat(String format, Object... arguments) {
    return arrayFormat(format, arguments);
  }

  private static final char DELIM_START = '{';
  private static final String DELIM_STR = "{}";
  private static final char ESCAPE_CHAR = '\\';

  /**
   * @param messagePattern The message pattern which will be parsed and formatted.
   *          
   * @param argArray  An array of arguments to be substituted in place of formatting anchors.
   *         
   *          
   * @return The formatted message
   */
  private final static String arrayFormat(final String messagePattern, final Object[] argArray) {

    if (TBFString.stringIsNullOrEmpty(messagePattern)) {
      return TBFConstants.EMPTY_STRING;
    }

    if (argArray == null) {
      return messagePattern;
    }

    // Use string builder for better multicore performance. 
    StringBuilder sb = new StringBuilder(messagePattern.length() + 50);

    int i = 0;
    for (int loop = 0; loop < argArray.length; loop++) {
      int j = messagePattern.indexOf(DELIM_STR, i);

      if (j == TBFConstants.NOT_FOUND) {
        // No more variables.
        if (i == 0) { // This is a simple string.
          return messagePattern;
        } else { // Add the tail string which contains no variables and returns the result.
          sb.append(messagePattern.substring(i, messagePattern.length()));
          return sb.toString();
        }

      } else {
        if (isEscapedDelimeter(messagePattern, j)) {
          if (!isDoubleEscaped(messagePattern, j)) {
            loop--; // DELIM_START was escaped, thus should not be incremented.
            sb.append(messagePattern.substring(i, j - 1));
            sb.append(DELIM_START);
            i = j + 1;
          } else {
            // The escape character preceding the delimiter start is
            // itself escaped: "abc x:\\{}".
            // We have to consume one backward slash.
            sb.append(messagePattern.substring(i, j - 1));
            deeplyAppendParameter(sb, argArray[loop], new HashMap<Object[], Object>());
            i = j + 2;
          }
        } else {
          // Normal case.
          sb.append(messagePattern.substring(i, j));
          deeplyAppendParameter(sb, argArray[loop], new HashMap<Object[], Object>());
          i = j + 2;
        }
      }
    }
    // Append the characters following the last {} pair.
    sb.append(messagePattern.substring(i, messagePattern.length()));
    return sb.toString();
  }

  private final static boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
    if (delimeterStartIndex == 0) {
      return false;
    }
    return (ESCAPE_CHAR == messagePattern.charAt(delimeterStartIndex - 1));
  }

  private final static boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
    return (delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR);
  }

  // Special treatment of array values was suggested by 'lizongbo'.
  private static void deeplyAppendParameter(StringBuilder sb, Object o, Map<Object[], ?> seenMap) {
    if (o == null) {
      sb.append("null");
      return;
    }
    if (!o.getClass().isArray()) {
      safeObjectAppend(sb, o);
    } else {
      // Check for primitive array types because they
      // unfortunately cannot be cast to Object[].
      if (o instanceof boolean[]) {
        booleanArrayAppend(sb, (boolean[]) o);
      } else if (o instanceof byte[]) {
        byteArrayAppend(sb, (byte[]) o);
      } else if (o instanceof char[]) {
        charArrayAppend(sb, (char[]) o);
      } else if (o instanceof short[]) {
        shortArrayAppend(sb, (short[]) o);
      } else if (o instanceof int[]) {
        intArrayAppend(sb, (int[]) o);
      } else if (o instanceof long[]) {
        longArrayAppend(sb, (long[]) o);
      } else if (o instanceof float[]) {
        floatArrayAppend(sb, (float[]) o);
      } else if (o instanceof double[]) {
        doubleArrayAppend(sb, (double[]) o);
      } else {
        objectArrayAppend(sb, (Object[]) o, seenMap);
      }
    }
  }

  private static void safeObjectAppend(StringBuilder sb, Object o) {
    try {
      String oAsString = o.toString();
      sb.append(oAsString);
    } catch (Throwable t) {
      log.error("Failed toString() invocation on an object of type [" + o.getClass().getName() + "]");
    }
  }

  private static void objectArrayAppend(StringBuilder sb, Object[] a, Map<Object[], ?> seenMap) {
    sb.append('[');
    if (!seenMap.containsKey(a)) {
      seenMap.put(a, null);
      final int len = a.length;
      for (int i = 0; i < len; i++) {
        deeplyAppendParameter(sb, a[i], seenMap);
        if (i != len - 1) {
          sb.append(", ");
        }
      }
      // Allow repeats in siblings.
      seenMap.remove(a);
    } else {
      sb.append("...");
    }
    sb.append(']');
  }

  private static void booleanArrayAppend(StringBuilder sb, boolean[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void byteArrayAppend(StringBuilder sb, byte[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void charArrayAppend(StringBuilder sb, char[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void shortArrayAppend(StringBuilder sb, short[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void intArrayAppend(StringBuilder sb, int[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void longArrayAppend(StringBuilder sb, long[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void floatArrayAppend(StringBuilder sb, float[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }

  private static void doubleArrayAppend(StringBuilder sb, double[] a) {
    sb.append('[');
    final int len = a.length;
    for (int i = 0; i < len; i++) {
      sb.append(a[i]);
      if (i != len - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
  }
}
