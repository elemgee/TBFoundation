/*
 * moved from Wonder (TB Community 2014)
 */
package org.treasureboat.foundation;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import com.webobjects.foundation.NSSelector;

/**
 * <p>
 * TBFComparatorSelector allows you to pass an arbitrary Comparator instance as an NSSelector into EOSortOrdering for use
 * with an in-memory sort. Note that this WILL NOT work for sorting directly in the database. This exploits the fact 
 * that EOSortOrdering calls selector.invoke(left, right) when you pass it an arbitrary selector to use.
 * </p>
 * 
 * <pre>
 * Example: ERXArrayUtilities.sortedArraySortedWithKey(array, "someKey", new TBFComparatorSelector(Collator.getInstance()))
 * </pre>
 * 
 * @author mschrag
 */
public class TBFComparatorSelector extends NSSelector {
  /**
   * Do I need to update serialVersionUID?
   * See section 5.6 <cite>Type Changes Affecting Serialization</cite> on page 51 of the 
   * <a href="http://java.sun.com/j2se/1.4/pdf/serial-spec.pdf">Java Object Serialization Spec</a>
   */
  private static final long serialVersionUID = 1L;

  private Comparator _comparator;

  //********************************************************************
  //  Constructor : コンストラクタ
  //********************************************************************

  /**
   * Constructs a new TBFComparatorSelector.
   * 
   * @param comparator the backing comparator to sort with 
   */
  public TBFComparatorSelector(Comparator comparator) {
    super("TBFComparatorSelector:" + comparator.getClass().getSimpleName(), null);
    _comparator = comparator;
  }

  //********************************************************************
  //  Methods : メソッド
  //********************************************************************

  @Override
  public Object invoke(Object target, Object argument) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
    return Integer.valueOf(_comparator.compare(target, argument));
  }
}
