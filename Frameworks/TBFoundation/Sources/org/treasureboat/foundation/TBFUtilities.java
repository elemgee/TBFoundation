/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import java.lang.reflect.Method;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSSet;

public class TBFUtilities {

  /**
   * Returns a deep clone of the given object.  A deep clone will attempt 
   * to clone any contained values (in the case of an NSArray or NSDictionary)
   * as well as the value itself.
   * 
   * @param obj
   *        the object to clone
   * @param onlyCollections
   *        if true, only collections will be cloned, not individual values
   * 
   * @return
   *        a deep clone of obj
   */
  public static <T> T deepClone(T obj, boolean onlyCollections) {
    Object clone;
    if (obj instanceof NSArray) {
      clone = TBFArrayUtilities.deepClone((NSArray) obj, onlyCollections);
    }
    else if (obj instanceof NSSet) {
      clone = TBFArrayUtilities.deepClone((NSSet) obj, onlyCollections);
    }
    else if (obj instanceof NSDictionary) {
      clone = TBFDictionaryUtilities.deepClone((NSDictionary) obj, onlyCollections);
    }
    else if (!onlyCollections && obj instanceof Cloneable) {
      try {
        Method m = obj.getClass().getMethod("clone", TBFConstants.EmptyClassArray);
        clone = m.invoke(obj, TBFConstants.EmptyObjectArray);
      }
      catch (Exception e) {
        throw new RuntimeException("Failed to clone " + obj + ".", e);
      }
    }
    else {
      clone = obj;
    }
    return (T) clone;
  }

}
