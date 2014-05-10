/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSPropertyListSerialization;

public class TBFValueUtilities {

  /**
   * Returns whether or not the given object is null or NSKVC.Null.
   * 
   * @param obj The object to check.
   * 
   * @return true if the object is null or NSKVC.Null
   */
  public static boolean isNull(Object obj) {
    return obj == null || obj == NSKeyValueCoding.NullValue || obj instanceof NSKeyValueCoding.Null;
  }

  //***********************************************************
  // int
  //***********************************************************

  /**
   * Basic utility method for reading int values. The current implementation
   * uses {@link #intValueWithDefault(Object, int)} with a default of
   * <code>0</code>.
   * 
   * @param obj The object to be evaluated.
   *            
   *            
   * @return integer evaluation of the given object.
   */
  public static int intValue(Object obj) {
    return TBFValueUtilities.intValueWithDefault(obj, 0);
  }

  /**
   * Basic utility method for reading <code>int</code> values. The current
   * implementation tests if the object is an instance of a String, Number and
   * Boolean. Booleans are 1 if they equal <code>true</code>. The default
   * value is used if the object is null or the boolean value is false.
   * 
   * @param obj The object to be evaluated.
   *            
   * @param def The default value if object is null.
   *            
   *            
   * @return integer evaluation of the given object.
   */
  public static int intValueWithDefault(Object obj, int def) {
    return TBFValueUtilities.isNull(obj) ? def : IntegerValueWithDefault(obj, Integer.valueOf(def));
  }

  /**
   * Basic utility method for reading <code>Integer</code> values. The current
   * implementation tests if the object is an instance of a String, Number and
   * Boolean. Booleans are 1 if they equal <code>true</code>. The default
   * value is used if the object is null or the boolean value is false.
   * 
   * @param obj The object to be evaluated.
   *            
   * @param def The default value if object is null.
   *            
   *            
   * @return Integer evaluation of the given object.
   */
  public static Integer IntegerValueWithDefault(Object obj, Integer def) {
    Integer value = def;
    if (!TBFValueUtilities.isNull(obj)) {
      if (obj instanceof Integer) {
        value = ((Integer) obj).intValue();

      } else if (obj instanceof Number) {
        value = Integer.valueOf(((Number) obj).intValue());

      } else if (obj instanceof String) {
        try {
          String strValue = ((String) obj).trim(); // Need to trim
          // trailing
          // space
          if (strValue.length() > 0) {
            value = Integer.valueOf(strValue);
          }
        } catch (NumberFormatException numberformatexception) {
          throw new IllegalArgumentException("Failed to parse an integer from the value '" + obj + "'.", numberformatexception);
        }
      } else if (obj instanceof Boolean) {
        value = ((Boolean) obj).booleanValue() ? Integer.valueOf(1) : def;
      }
    } else {
      value = def;
    }
    return value;
  }

  //********************************************************************
  //  Array
  //********************************************************************

  /**
   * Basic utility method for reading NSArray values which works also with
   * Strings. The current implementation uses
   * {@link #arrayValueWithDefault(Object, NSArray)} with a default of
   * <code>null</code>.
   * 
   * @param obj The object to be evaluated.
   *            
   * @return NSArray evaluation of the given object.
   * 
   */
  public static NSArray arrayValue(Object obj) {
    return TBFValueUtilities.arrayValueWithDefault(obj, null);
  }

  /**
   * Basic utility method for reading <code>NSArray</code> values which also
   * works with serialized NSArrays and comma separated items. The default
   * value is used if the object is null.
   * 
   * @param obj The object to be evaluated.
   *            
   * @param def The default value if object is null.
   *            
   * @return NSArray evaluation of the given object.
   * 
   */
  public static NSArray arrayValueWithDefault(Object obj, NSArray def) {
    NSArray value = def;
    if (!isNull(obj)) {
      if (obj instanceof NSArray) {
        value = (NSArray) obj;
      } else if (obj instanceof String) {
        String strValue = ((String) obj).trim();
        if (strValue.length() > 0) {
          if (strValue.charAt(0) != '(') {
            strValue = "(" + strValue + ")";
          }
          // convert " to ' for convenience
          strValue = strValue.replace('\'', '"');

          value = (NSArray) NSPropertyListSerialization.propertyListFromString(strValue);
          if (value == null) {
            throw new IllegalArgumentException("Failed to parse an array from the value '" + obj + "'.");
          }
        }
      } else {
        throw new IllegalArgumentException("Failed to parse an array from the value '" + obj + "'.");
      }
    }
    return value;
  }

}
