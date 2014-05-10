package org.treasureboat.foundation.image;

import org.treasureboat.foundation.TBFTarget;
import org.treasureboat.foundation.constants.TBFKnownFrameworkNames;
import org.treasureboat.foundation.enums.TBFUriSchemaEnums;

/**
 * After programming this long in WO, I always found it strange
 * that everything is an Object but we don't really have Objects for
 * Images. Images have so many Parameters like src, width ...
 * Really strange, but now here is it.
 * 
 * @author ishimoto
 */
public class TBFImage {

  public static final Integer AUTOMATIC = 0;

  public static final String RETINA = "@2x";
  public static final String SAMPLE_IMAGE_PATH = TBFUriSchemaEnums.Static.schema() + TBFKnownFrameworkNames.TBCONTENTSDELIVERY + ":images/logo/tb@2x.png";

  //********************************************************************
  //  Constructor : コンストラクタ
  //********************************************************************

  public TBFImage() {
    this(SAMPLE_IMAGE_PATH);
  }

  public TBFImage(String locationURI) {
    this(locationURI, null);
  }

  public TBFImage(String locationURI, String alt) {
    this(locationURI, alt, AUTOMATIC, AUTOMATIC);
  }

  public TBFImage(String locationURI, String alt, int width) {
    this(locationURI, alt, width, AUTOMATIC);
  }

  public TBFImage(String locationURI, String alt, int width, int height) {
    this(locationURI, alt, width, height, null);
  }

  public TBFImage(String locationURI, String alt, int width, int height, String linkURI) {
    this(locationURI, alt, width, height, linkURI, TBFTarget.NULL);
  }

  public TBFImage(String locationURI, String alt, int width, int height, String linkURI, String target) {
    this(locationURI, alt, width, height, linkURI, target, null);
  }

  public TBFImage(String locationURI, String alt, int width, int height, String linkURI, String target, String css) {
    this(locationURI, alt, width, height, linkURI, target, css, null);
  }

  public TBFImage(String locationURI, String alt, int width, int height, String linkURI, String target, String css, String style) {
    setLocationURI(locationURI);
    setAlt(alt);
    setWidth(width);
    setHeight(height);
    setLinkURI(linkURI);
    setTarget(target);
    setCss(css);
    setStyle(style);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('<');
    sb.append(TBFImage.class.getName());
    sb.append(": ");
    sb.append("locationURI = '");
    sb.append(locationURI());
    sb.append("'");
    sb.append(", alt = '");
    sb.append(alt());
    sb.append("'");
    sb.append(", width = ");
    sb.append(width());
    sb.append(", height = ");
    sb.append(height());
    sb.append(", linkURI = ");
    sb.append(linkURI());
    sb.append(", target = ");
    sb.append(target());
    sb.append(", css = ");
    sb.append(css());
    sb.append(", style = ");
    sb.append(style());
    sb.append(", isRetina = ");
    sb.append(isRetina());
    sb.append(", hasToBeLocalized = ");
    sb.append(hasToBeLocalized());
    sb.append('>');
    return sb.toString();
  }

  //********************************************************************
  //  API Methods : API メソッド
  //********************************************************************

  private void setLocationURI(String locationURI) {
    if (locationURI.contains(RETINA)) {
      isRetina = true;

      // TODO REMOVE : call it with Retina for now. the caller can add the @2x syntax
      // and we will implement the Retina version later correctly.
      locationURI = locationURI.replace("@2x", "");



    }
    this.locationURI = locationURI;
  }
  public String locationURI() {
    return locationURI;
  } 
  private String locationURI = null;

  private void setAlt(String alt) {
    this.alt = alt;
  }
  public String alt() {
    return alt;
  }
  private String alt = null;

  private void setWidth(int width) {
    this.width = width;
  }
  public int width() {
    return width;
  }
  private int width = AUTOMATIC;

  private void setHeight(int height) {
    this.height = height;
  }
  public int height() {
    return height;
  }
  private int height = AUTOMATIC;

  private void setLinkURI(String linkURI) {
    this.linkURI = linkURI;
  }
  public String linkURI() {
    return linkURI;
  }
  private String linkURI = null;

  private void setTarget(String target) {
    this.target = target;
  }
  public String target() {
    return target;
  }
  private String target = null;

  private void setCss(String css) {
    this.css = css;
  }
  public String css() {
    return css;
  }
  private String css = null;

  private void setStyle(String style) {
    this.style = style;
  }
  public String style() {
    return style;
  }
  private String style = null;

  //********************************************************************
  //  Methods : メソッド
  //********************************************************************

  public void setHasToBeLocalized(boolean hasToBeLocalized) {
    this.hasToBeLocalized = hasToBeLocalized;
  }
  public boolean hasToBeLocalized() {
    return hasToBeLocalized;
  }
  private boolean hasToBeLocalized = false;

  public boolean isRetina() {
    return isRetina;
  }
  private boolean isRetina = false;
}
