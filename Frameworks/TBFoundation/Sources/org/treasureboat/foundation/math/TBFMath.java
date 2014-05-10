package org.treasureboat.foundation.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.treasureboat.foundation.TBFConstants;

public class TBFMath {

  //********************************************************************
  //  cubicMeter
  //********************************************************************

  public static final float CUBIC_EMPTY = 0.0f;

  public static float cubicMeter(Integer lengthMilliMeter, Integer widthMilliMeter, Integer heightMilliMeter) {
    if(lengthMilliMeter == null || widthMilliMeter == null || heightMilliMeter == null) {
      return CUBIC_EMPTY;
    }
    return cubicMeter(lengthMilliMeter.intValue(), widthMilliMeter.intValue(), heightMilliMeter.intValue());
  }

  public static float cubicMeter(int lengthMilliMeter, int widthMilliMeter, int heightMilliMeter) {
    return cubicMeter(lengthMilliMeter, widthMilliMeter, heightMilliMeter, 1);
  } 

  public static float cubicMeter(int lengthMilliMeter, int widthMilliMeter, int heightMilliMeter, int count) {
    long result = (long) lengthMilliMeter * (long) widthMilliMeter * (long) heightMilliMeter * (long) count;
    return (float) result / 1000000000;    
  } 

  public static String cubicMeterAsString(Integer lengthMilliMeter, Integer widthMilliMeter, Integer heightMilliMeter) {
    if(lengthMilliMeter == null || widthMilliMeter == null || heightMilliMeter == null) {
      return cubicMeterAsString(CUBIC_EMPTY);
    }
    return cubicMeterAsString(lengthMilliMeter.intValue(), widthMilliMeter.intValue(), heightMilliMeter.intValue());
  }

  public static String cubicMeterAsString(int lengthMilliMeter, int widthMilliMeter, int heightMilliMeter) {
    float f = cubicMeter(lengthMilliMeter, widthMilliMeter, heightMilliMeter);
    return cubicMeterAsString(f);
  } 

  public static String cubicMeterAsString(float cubicMeter) {
    BigDecimal big = new BigDecimal(cubicMeter, new MathContext(3, RoundingMode.CEILING));
    return big.setScale(3, BigDecimal.ROUND_HALF_UP).toString() + TBFConstants.SPACE + TBFConstants.HTML_CubicMeter;
  }

}
