/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import java.util.Enumeration;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSKeyValueCodingAdditions;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSMutableSet;
import com.webobjects.foundation.NSSet;

public class TBFArrayUtilities {

  /**
   * Starting with an array of KeyValueCoding-compliant objects and a keyPath,
   * this method calls valueForKey on each object in the array and groups the
   * contents of the array, using the result of the valueForKey call as a key
   * in a dictionary. If passed a null array, null is returned. If passed a null
   * keyPath, an empty dictionary is returned. If valueKeyPath is not null, then
   * the grouped arrays each have valueForKey called with valueKeyPath and the
   * grouped arrays are replaced with the results of that call.
   *
   * <p>If one starts with:
<pre>( { lastName = "Barker"; firstName = "Bob"; favoriteColor = "blue"; },
{ firstName = "Bob"; favoriteColor = "red"; },
{ lastName = "Further"; firstName = "Frank"; favoriteColor = "green"; } )</pre>
   * and one calls arrayGroupedByKeyPath(objects, "firstName", null, "favoriteColor"), one gets:
<pre>{Frank = ("green"); Bob = ("blue", "red");</pre>
   * If one calls arrayGroupedByKeyPath(objects, "lastName", "extra", "favoriteColor"), one gets:
<pre>{Further = ("green"); Barker = ("blue"); "extra" = ("red"); }</pre>
   * If one calls arrayGroupedByKeyPath(objects, "lastName", null, "favoriteColor"), one gets:
<pre>{Further = ("green"); Barker = ("blue"); "**** NULL GROUPING KEY ****" = ("red"); }</pre></p>
   *
   * @param objects array of objects to be grouped
   * @param keyPath path into objects used to group the objects
   * @param nullGroupingKey used as the key in the results dictionary
   *        for the array of objects for which the valueForKey with keyPath
   *        result is null.
   * @param valueKeyPath used to call valueForKey on the arrays in
   *        the results dictionary, with the results of those calls each
   *        replacing the corresponding array in the results dictionary.
   * @return a dictionary where the keys are the grouped values and the
   *          objects are arrays of the objects that have that value.
   *          Objects for which the key path returns null will be grouped
   *          with the key {@link #NULL_GROUPING_KEY}
   */
  public static NSDictionary arrayGroupedByKeyPath(NSArray objects, String keyPath, Object nullGroupingKey, String valueKeyPath) {
    if (objects == null) {
      return null;
    }
    NSMutableDictionary result = new NSMutableDictionary();
    for (Enumeration e = objects.objectEnumerator(); e.hasMoreElements();) {
      Object eo = e.nextElement();
      Object key = NSKeyValueCodingAdditions.Utility.valueForKeyPath(eo,keyPath);
      boolean isNullKey = key == null || key instanceof NSKeyValueCoding.Null;
      if (!isNullKey || nullGroupingKey != null) {
        if (isNullKey) {
          key = nullGroupingKey;
        }
        NSMutableArray existingGroup = (NSMutableArray) result.objectForKey(key);
        if (existingGroup == null) {
          existingGroup=new NSMutableArray();
          result.setObjectForKey(existingGroup,key);
        }
        if (valueKeyPath != null) {
          Object value = NSKeyValueCodingAdditions.Utility.valueForKeyPath(eo,valueKeyPath);
          if (value != null) {
            existingGroup.addObject(value);
          }
        } else {
          existingGroup.addObject(eo);
        }
      }
    }
    return result;
  }

  /**
   * Simple comparison method to see if two array
   * objects are identical sets.
   * 
   * @param a1 
   *        first array
   * @param a2 
   *        second array
   * @return 
   *        result of comparison
   */
  public static <T> boolean arraysAreIdenticalSets(NSArray<? super T> a1, NSArray<? super T> a2) {
    if (a1 == null || a2 == null) {
      return a1 == a2;
    }
    boolean result=true;
    for (Enumeration<? super T> e = a1.objectEnumerator(); e.hasMoreElements();) {
      Object i = e.nextElement();
      if (!a2.containsObject(i)) {
        result=false; break;
      }
    }
    if (result) {
      for (Enumeration<? super T> e = a2.objectEnumerator(); e.hasMoreElements();) {
        Object i = e.nextElement();
        if (!a1.containsObject(i)) {
          result = false;
          break;
        }
      }
    }
    return result;
  }


  /**
   * 配列要素が存在するか確認します。
   * 
   * @param aNSArray
   *        NSArray
   * 
   * @return 
   *        配列が存在すれば true が戻ります
   */
  public static boolean arrayExists(NSArray<?> aNSArray) {
    return !TBFArrayUtilities.arrayIsNullOrEmpty(aNSArray);
  }

  /**
   * 配列の各要素を toString 処理し、文字列配列として戻します。
   * 
   * @param a
   *        配列
   * 
   * @return
   *        文字列配列
   */
  public static String[] toStringArray(NSArray<?> a) {
    String[] b = new String[a.count()];
    for (int i = a.count(); i-- > 0; b[i] = a.objectAtIndex(i).toString()) {
      // do nothing
    }
    return b;
  }

  /**
   * Swaps two objects a and b in an array inplace 
   * 
   * @param array
   *        the array 
   * @param a
   *        first object
   * @param b
   *        second object
   * 
   * @throws {@link RuntimeException} if one or both indexes are out of bounds
   * 
   * @author cug - Jan 7, 2008
   */
  //    public static <T> void swapObjectsInArray (NSMutableArray<T> array, T a, T b) {
  //      if (array == null || array.count() < 2) {
  //        throw new RuntimeException ("Array is either null or does not have enough elements.");
  //      }
  //      int indexOfA = array.indexOf(a);
  //      int indexOfB = array.indexOf(b);
  //  
  //      if (indexOfA >= 0 && indexOfB >= 0) {
  //        swapObjectsAtIndexesInArray(array, indexOfA, indexOfB);
  //      } else {
  //        throw new RuntimeException ("At least one of the objects is not element of the array!");
  //      }
  //    }

  /**
   * Swaps two objects at the given indexes in an array inplace 
   * 
   * @param array
   *        the array 
   * @param indexOfA
   *        index of the first object
   * @param indexOfB
   *        index of the second object
   * 
   * @throws {@link RuntimeException} if one or both indexes are out of bounds
   * 
   * @author cug - Jan 7, 2008
   */
  //  public static <T> void swapObjectsAtIndexesInArray (NSMutableArray<T> array, int indexOfA, int indexOfB) {
  //    try {
  //      T tmp = array.replaceObjectAtIndex(array.objectAtIndex(indexOfA), indexOfB);
  //      array.replaceObjectAtIndex(tmp, indexOfA);
  //    }
  //    catch (Exception e) {
  //      throw new RuntimeException();
  //    }
  //  }

  /**
   * Swaps the object a with the object at the given index
   *
   * @param array 
   *        the array
   * @param a
   *        first object
   * @param indexOfB
   *        index of second object
   * 
   * @author edgar - Apr 14, 2008
   */
  //  public static <T> void swapObjectWithObjectAtIndexInArray(NSMutableArray<T> array, T a, int indexOfB) {
  //    if (array == null || array.count() < 2) {
  //      throw new RuntimeException ("Array is either null or does not have enough elements.");
  //    }
  //
  //    int indexOfA = array.indexOf(a);
  //
  //    if (indexOfA >= 0 && indexOfB >= 0) {
  //      if (indexOfA != indexOfB) {
  //        swapObjectsAtIndexesInArray(array, indexOfA, indexOfB);
  //      }
  //    }
  //    else {
  //      throw new RuntimeException ("At least one of the objects is not element of the array!");
  //    }
  //  }

  /**
   * Returns a deep clone of the given array.  A deep clone will attempt 
   * to clone the values of this array as well as the array itself.
   * 
   * @param array 
   *        the array to clone
   * @param onlyCollections 
   *        if true, only collections in this array will be cloned, not individual values
   * 
   * @return 
   *        a deep clone of array
   */
  public static <T> NSArray<T> deepClone(NSArray<T> array, boolean onlyCollections) {
    NSMutableArray<T> clonedArray = null;
    if (array != null) {
      clonedArray = array.mutableClone();
      for (int i = array.count() - 1; i >= 0; i --) {
        T value = array.objectAtIndex(i);
        T clonedValue = TBFUtilities.deepClone(value, onlyCollections);
        if (clonedValue != null) {
          if (clonedValue != value) {
            clonedArray.replaceObjectAtIndex(clonedValue, i);
          }
        }
        else {
          clonedArray.removeObjectAtIndex(i);
        }
      }
    }
    return clonedArray;
  }

  /**
   * Returns a deep clone of the given set.  A deep clone will attempt 
   * to clone the values of this set as well as the set itself.
   * 
   * @param set 
   *        the set to clone
   * @param onlyCollections 
   *        if true, only collections in this array will be cloned, not individual values
   * 
   * @return 
   *        a deep clone of set
   */
  public static <T> NSSet<T> deepClone(NSSet<T> set, boolean onlyCollections) {
    NSMutableSet<T> clonedSet = null;
    if (set != null) {
      clonedSet = set.mutableClone();
      for (T value : set) {
        T clonedValue = TBFUtilities.deepClone(value, onlyCollections);
        if (clonedValue != null) {
          if (clonedValue != value) {
            clonedSet.removeObject(value);
            clonedSet.addObject(clonedValue);
          }
        } else {
          clonedSet.removeObject(value);
        }
      }
    }
    return clonedSet;
  }

  /**
   * Check if an NSArray is null or Empty
   * <hr />
   * 
   * 配列が null か空かをチェックします
   * <br />
   * 
   * @param aNSArray 
   *        NSArray
   * 
   * @return 
   *        if an NSArray is null or Empty <code>true</code> returns<br />
   *        配列が null か空の場合は <code>true</code> が戻ります
   */
  public static boolean arrayIsNullOrEmpty(NSArray<?> aNSArray) {
    if(aNSArray == null || aNSArray.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   *  To create oneLine Log for an NSArray&lt;String&gt;
   *  <hr />
   *  
   *  NSArray 配列をログとして出力する時に複数行に渡らないで、一行で収まるように
   * <br />
   * 
   *  @param aNSArray 
   *          NSArray
   * 
   *  @return 
   *          change a NSArray to String<br />
   *          NSArray を String に変換した行
   */
  public static String arrayToLogstring(NSArray<String> aNSArray){
    String result = "( ";

    for(String obj : aNSArray) {
      result += obj + ", ";
    }
    result = result.substring(0, result.length() - 2);
    result += " )";

    return result;
  }

  /**
   * Reverses the elements of an array
   * 
   * @param array 
   *        to be reversed
   * @return 
   *        reverse ordered array
   */
  public static <T> NSArray<T> reverse(NSArray<T> array) {
    NSArray<T> reverse = null;
    if (array != null && array.count() > 0) {
      NSMutableArray<T> reverseTemp = new NSMutableArray<T>(array.count());
      for (Enumeration<T> reverseEnumerator = array.reverseObjectEnumerator(); reverseEnumerator.hasMoreElements();) {
        reverseTemp.addObject(reverseEnumerator.nextElement());
      }
      reverse = reverseTemp.immutableClone();
    }
    return reverse != null ? reverse : NSArray.EmptyArray;
  }

  /**
   * The BaseOperator is Wonder's core class of 
   * {@link com.webobjects.foundation.NSArray.Operator NSArray.Operator}. 
   * This class adds support for chaining multiple array operators in a single 
   * keypath via its 
   * {@link er.extensions.foundation.ERXArrayUtilities.BaseOperator#contents(NSArray, String) contents} 
   * method.<br/>
   */
  static abstract class BaseOperator implements NSArray.Operator {

    /**
     * Rather than iterating through the array argument calling
     * {@link com.webobjects.foundation.NSKeyValueCodingAdditions.Utility#valueForKeyPath(Object, String) valueForKeyPath}
     * on each array object, this method operates by calling
     * {@link com.webobjects.foundation.NSArray#valueForKeyPath(String) valueForKeyPath}
     * on the array argument instead.  This method is used by Wonder operators to chain 
     * multiple array operators in a single key path.
     * 
     * @param array 
     *        the array value for the operator
     * @param keypath 
     *        the keypath to call on the array argument 
     * @return 
     *        the object value produced by valueForKeyPath, or the array itself
     *        if the keypath is empty
     */
    public Object contents(NSArray<?> array, String keypath) {
      if(array != null && array.count() > 0  && keypath != null && keypath.length() > 0) {
        return NSKeyValueCodingAdditions.Utility.valueForKeyPath(array, keypath);
      }
      return array;
    }
  }

  /**
   * Define an {@link com.webobjects.foundation.NSArray.Operator NSArray.Operator} 
   * for the key <b>reverse</b>.<br/>
   * <br/>
   * This allows for key value paths like:<br/>
   * <br/>
   * <code>myArray.valueForKey("@reverse.someOtherPath");</code><br/>
   * <br/>
   * which would reverse the order of the array myArray before continuing to
   * process someOtherPath.
   * 
   * @see BaseOperator
   */
  public static class ReverseOperator extends BaseOperator {
    public ReverseOperator() {
    }

    /**
     * returns the reverse value for the values of the keypath.
     * @param array 
     *        array to be checked.
     * @param keypath 
     *        additional keypath
     * @return 
     *        value produced following keypath after array is reversed
     */
    public Object compute(NSArray array, String keypath) {
      array = reverse(array);
      return contents(array, keypath);
    }
  }

}
