package org.treasureboat.foundation.enums;

import org.treasureboat.foundation.TBFString;
import org.treasureboat.foundation.enums.iface.TBFLocalizableInterface;
import org.treasureboat.foundation.enums.iface.TBFVersionInterface;

import com.webobjects.foundation.NSKeyValueCodingAdditions;

/**
 * org.treasureboat.foundation.enums.TBFUriSchemaEnums
 */
public enum TBFUriSchemaEnums implements TBFLocalizableInterface, TBFVersionInterface{

  /* StaticResource, HyperlinkResource */
  DirectAction("da://"), 

  /* StaticResource, HyperlinkResource */
  DirectActionAlias("wa://"), 

  /* StaticResource */
  Attachment("attachment://"),

  /* StaticResource */
  Static("static://"),

  /* StaticResource */
  Face("face://"),

  /* StaticResource, HyperlinkResource */
  Http("http://"),

  /* StaticResource, HyperlinkResource */
  Https("https://"),

  /* HyperlinkResource */
  Ftp("ftp://"),

  /* HyperlinkResource */
  MailTo("mailto:"),

  /* HyperlinkResource */
  MailToAlias("mailto://"),

  /* HyperlinkResource */
  D2w("d2w://"),

  /* HyperlinkResource */
  D3w("d3w://"),

  File("file://");

  //********************************************************************
  //  Constants
  //********************************************************************

  public static final String SCHEMA_SEPERATOR = "://";

  //********************************************************************
  //  Constructor : コンストラクタ
  //********************************************************************

  private TBFUriSchemaEnums(String schema) {
    this.schema = schema;
  }

  //********************************************************************
  //  Methods : メソッド
  //********************************************************************

  public String schema() {
    return schema;
  }
  private String schema;

  //********************************************************************
  //  static Methods : static メソッド
  //********************************************************************

  public static boolean isDirectAction(String s) {
    return !TBFString.stringIsNullOrEmpty(s) && (s.startsWith(TBFUriSchemaEnums.DirectAction.schema()) || s.startsWith(TBFUriSchemaEnums.DirectActionAlias.schema()));
  }

  public static boolean isHttpOrHttps(String s) {
    return !TBFString.stringIsNullOrEmpty(s) && (s.startsWith(TBFUriSchemaEnums.Http.schema()) || s.startsWith(TBFUriSchemaEnums.Https.schema()));
  }

  public static boolean isAttachment(String s) {
    return !TBFString.stringIsNullOrEmpty(s) && s.startsWith(TBFUriSchemaEnums.Attachment.schema());
  }

  public static boolean isStatic(String s) {
    return !TBFString.stringIsNullOrEmpty(s) && s.startsWith(TBFUriSchemaEnums.Static.schema());
  }

  public static boolean isFace(String s) {
    return !TBFString.stringIsNullOrEmpty(s) && s.startsWith(TBFUriSchemaEnums.Face.schema());
  }

  public static String createStatic(String frameworkName, String fileName) {
    StringBuilder sb = new StringBuilder();
    sb.append(TBFUriSchemaEnums.Static.schema());
    if(!TBFString.stringIsNullOrEmpty(frameworkName)) {
      sb.append(frameworkName);
      sb.append(':');
    }
    sb.append(fileName);
    return sb.toString();
  }

  //********************************************************************
  //  Implements TBFLocalizableInterface
  //********************************************************************

  @Override
  public String localizableKey() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(NSKeyValueCodingAdditions.KeyPathSeparator);
    sb.append(name());
    return sb.toString();
  }

  //********************************************************************
  //  Implements TBFVersionInterface
  //********************************************************************

  @Override
  public int versionNumber() {
    return 1;
  }

}
