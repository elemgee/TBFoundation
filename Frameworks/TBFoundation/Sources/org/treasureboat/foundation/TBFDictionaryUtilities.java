/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

public class TBFDictionaryUtilities {

  /**
   * Returns a deep clone of the given dictionary.  A deep clone will attempt to 
   * clone the keys and values (deeply) of this dictionary as well as the 
   * dictionary itself.
   * 
   * @param dict
   *        the dictionary to clone
   * @param onlyCollections
   *        if true, only collections in this dictionary will be cloned, not individual values
   * 
   * @return 
   *        a deep clone of dict
   */
  public static <K, V> NSDictionary<K, V> deepClone(NSDictionary<K, V> dict, boolean onlyCollections) {
    NSMutableDictionary<K, V> clonedDict = null;
    if (dict != null) {
      clonedDict = dict.mutableClone();
      for (K key : dict.allKeys()) {
        V value = dict.objectForKey(key);
        K cloneKey = TBFUtilities.deepClone(key, onlyCollections);
        V cloneValue = TBFUtilities.deepClone(value, onlyCollections);
        if (cloneKey != key) {
          clonedDict.removeObjectForKey(key);
          if (cloneValue != null) {
            clonedDict.setObjectForKey(cloneValue, cloneKey);
          }
        } else if (cloneValue != null) {
          if (cloneValue != value) {
            clonedDict.setObjectForKey(cloneValue, cloneKey);
          }
        } else {
          clonedDict.removeObjectForKey(key);
        }
      }
    }
    return clonedDict;
  }

}
