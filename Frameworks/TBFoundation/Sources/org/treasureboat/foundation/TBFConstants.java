package org.treasureboat.foundation;

public class TBFConstants {

  public static final int NOT_FOUND = -1;

  public static final String EMPTY_STRING = "";
  public static final String NULL_STRING = "<NULL>";

  public static final String SPACE = " ";
  public static final String SLASH = "/";
  public static final String EQUALS = "=";
  public static final String AND = "&";
  public static final String UNDER_BAR  = "_";
  public static final String ASTERISK = "*";
  public static final String QUESTION = "?";
  public static final String LT = "<";
  public static final String GT = ">";
  public static final String DOUBLE_Q = "\"";
  public static final String SINGLE_Q = "'";

  public static final String COMMA = ",";
  public static final String DB_COMMA = "，";
  public static final String DOT = ".";

  public static final String TAB = "\t";
  public static final String LF = "\n";
  public static final String CR  = "\r";
  public static final String CRLF = CR + LF;

  public final static String HTML_Return = "<br />";

  public final static String HTML_CubicMeter = "m&sup3";

  public static final Class<?>[] EmptyClassArray = new Class[0];
  public static final Object[] EmptyObjectArray = new Object[] {};


  public static char[] HTMLStringReservedCharacters = { '&', '"', '<', '>' };
  public static String[] HTMLStringReservedEscapeSequences = { "&amp;", "&quot;", "&lt;", "&gt;" };





  protected final static boolean[] map = new boolean[128];

  static {
    map['&'] = true;
    map['"'] = true;
    map['<'] = true;
    map['>'] = true;
  }






  /**
   * half byte space
   * 半角スペース文字
   */
  public static char _SPACE_HANKAKU = ' ';

  /**
   * full byte space
   * 全角スペース文字
   */
  public static char _SPACE_ZENKAKU = '　';

  /**
   * spaces
   * スペース文字 (半角/全角)
   */
  public static char[] _SPACE_CHARS = {_SPACE_HANKAKU, _SPACE_ZENKAKU};

  /** 
   * 数値変換
   */
  public static final char[] NUMBER_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

  /** 
   * 全角数字
   */
  public static final String ZENKAKU_SUJI = "０１２３４５６７８９";

  /** 
   * 半角数字
   */
  public static final String HANKAKU_SUJI = "0123456789";

  /** 
   * 全角文字
   */
  public static final String ZENKAKU_MOJI = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";

  /** 
   * 半角文字
   */
  public static final String HANKAKU_MOJI = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  /** 
   * 全角記号
   */
  public static final String ZENKAKU_KIGO = "［］”？＜＞’＆％＃＊ー（）・！＄＝〜｛｝‘＋＿／．［］；：＠＾−￥　";

  /** 
   * 半角記号
   */
  public static final String HANKAKU_KIGO = "[]\"?<>'&%#*ｰ()･!$=~{}`+_/.[];:@^-\\ ";

  /** 
   * Holds the chars for hex encoding 
   */
  public static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  public static final char[] HEX_CHARS_BIGLETTERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

}
