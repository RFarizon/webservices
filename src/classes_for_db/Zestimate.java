/**
 * 
 */
package classes_for_db;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A class to represent a Zestimate record in the Real Estate DB.
 * 
 * @author brandonbogan
 *
 */
public class Zestimate implements DbTableObject {

  private BigInteger zpid;
  private int zestimate;
  private Date lastUpdated;
  private int thirtyDayChange;
  private int valuationHigh;
  private int valuationLow;
  private float percentileValue;

  private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

  /**
   * 
   */
  public Zestimate() {

  }

  public BigInteger getZpid() {
    return zpid;
  }


  /**
   * 
   * @param zpid A BigInteger representing the zpid of the property. This argument needs to be a
   *        {@code BigInteger} instead of an {@code int} because the size of some of the larger
   *        zpids exceeds the max value of an int.
   * @throws IllegalArgumentException if {@code zpid} is less than 0 or greater than 10000000000 (10
   *         billion).
   */
  public void setZpid(BigInteger zpid) {
    BigInteger min = new BigInteger("0");
    BigInteger max = new BigInteger("10000000000");
    if (zpid.compareTo(min) < 0) {
      throw new IllegalArgumentException(
          "zpid cannot be negative. Argument given for zpid: " + zpid);
    }
    if (zpid.compareTo(max) >= 0) {
      throw new IllegalArgumentException(
          "Argument given for zpid exceeds maximum allowed value of 10000000000 (10 billion). Argument given: "
              + zpid);
    } else {
      this.zpid = zpid;
    }
  }

  /**
   * 
   * @param zpid A String representation of this property's zpid
   * @throws IllegalArgumentException if {@code zpid} is longer than 11 characters, is less than 0,
   *         or is greater than 10000000000 (10 billion).
   */
  public void setZpid(String zpid) {
    if (zpid.length() > 11) {
      throw new IllegalArgumentException(
          "Argument for zpid exceeds maximum allowable length of 11 characters. Argument given: "
              + zpid);
    } else {
      BigInteger id = new BigInteger(zpid);
      this.setZpid(id);
    }
  }



  /**
   * @return the zestimate
   */
  public int getZestimate() {
    return zestimate;
  }

  /**
   * @param zestimate the zestimate to set
   * @throws IllegalArgumentException if {@code zestimate} is negative or greater than 1 Billion.
   */
  public void setZestimate(int zestimate) {
    if (zestimate < 0 | zestimate >= 1000000000) {
      throw new IllegalArgumentException(
          "Argument for zestimate falls outside of acceptable range of 0 to 1 Billion. Argument given: "
              + zestimate);
    } else {
      this.zestimate = zestimate;
    }
  }

  /**
   * @param A String representation of the zestimate to set
   * @throws IllegalArgumentException if {@code zestimate} is longer than 9 characters, is negative,
   *         or is greater than 1 Billion.
   */
  public void setZestimate(String zestimate) {
    if (zestimate.length() > 9) {
      throw new IllegalArgumentException(
          "Length of argument for zestimate exceeds max allowed number of characters (9). Argument given: "
              + zestimate);
    } else {
      if (zestimate.length() > 0) {
        int z = Integer.parseInt(zestimate);
        this.setZestimate(z);
      }
    }
  }

  /**
   * @return the date that the Zestimate for the property was last Updated
   */
  public String getLastUpdatedString() {
    return this.sdf.format(lastUpdated);
  }

  /**
   * @return the date that the Zestimate for the property was last Updated
   */
  public Date getLastUpdated() {
    return this.lastUpdated;
  }

  /**
   * @param lastUpdated the date that the property's Zestimate was last updated
   */
  public void setLastUpdated(String lastUpdated) {
    try {
      Date date = this.sdf.parse(lastUpdated);
      this.lastUpdated = date;
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * @param lastUpdated the date that the property's Zestimate was last updated
   */
  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @return the thirtyDayChange
   */
  public int getThirtyDayChange() {
    return thirtyDayChange;
  }

  /**
   * @param thirtyDayChange the thirtyDayChange to set
   * @throws IllegalArgumentException if {@code thirtyDayChange} lies outside of the allowed range
   *         of -$1 Billion < thirtyDayChange < $1 Billion.
   */
  public void setThirtyDayChange(int thirtyDayChange) {
    if (Math.abs(thirtyDayChange) >= 1000000000) {
      throw new IllegalArgumentException(
          "Argument for thirtyDayChange lies outside of the allowed range of -$1 Billion "
              + "< thirtyDayChange < $1 Billion. Argument given: " + thirtyDayChange);
    } else {
      this.thirtyDayChange = thirtyDayChange;
    }
  }

  /**
   * @param thirtyDayChange the thirtyDayChange to set
   * @throws IllegalArgumentException if {@code thirtyDayChange} lies outside of the allowed range
   *         of -$1 Billion < thirtyDayChange < $1 Billion.
   */
  public void setThirtyDayChange(String thirtyDayChange) {
    if (thirtyDayChange.length() > 0) {
      int change = Integer.parseInt(thirtyDayChange);
      this.setThirtyDayChange(change);
    }
  }

  /**
   * @return the valuationHigh
   */
  public int getValuationHigh() {
    return valuationHigh;
  }

  /**
   * @param valuationHigh the valuationHigh to set
   * @throws IllegalArgumentException if {@code valuationHigh} lies outside of the allowed range of
   *         -$1 Billion < thirtyDayChange < $1 Billion.
   */
  public void setValuationHigh(int valuationHigh) {
    if (Math.abs(valuationHigh) >= 1000000000) {
      throw new IllegalArgumentException(
          "Argument for valuationHigh lies outside of the allowed range of -$1 Billion "
              + "< valuationHigh < $1 Billion. Argument given: " + valuationHigh);
    } else {
      this.valuationHigh = valuationHigh;
    }
  }

  /**
   * @param valuationHigh the valuationHigh to set
   * @throws IllegalArgumentException if {@code valuationHigh} lies outside of the allowed range of
   *         -$1 Billion < thirtyDayChange < $1 Billion.
   */
  public void setValuationHigh(String valuationHigh) {
    if (valuationHigh.length() > 0) {
      int val = Integer.parseInt(valuationHigh);
      this.setValuationHigh(val);
    }
  }

  /**
   * @return the valuationLow
   */
  public int getvaluationLow() {
    return valuationLow;
  }

  /**
   * @param valuationLow the valuationLow to set
   * @throws IllegalArgumentException if {@code valuationLow} lies outside of the allowed range of
   *         -$1 Billion < thirtyDayChange < $1 Billion.
   */
  public void setvaluationLow(int valuationLow) {
    if (Math.abs(valuationLow) >= 1000000000) {
      throw new IllegalArgumentException(
          "Argument for valuationLow lies outside of the allowed range of -$1 Billion "
              + "< valuationLow < $1 Billion. Argument given: " + valuationLow);
    } else {
      this.valuationLow = valuationLow;
    }
  }

  /**
   * @param valuationLow the valuationLow to set
   * @throws IllegalArgumentException if {@code valuationLow} lies outside of the allowed range of
   *         -$1 Billion < thirtyDayChange < $1 Billion.
   */
  public void setvaluationLow(String valuationLow) {
    if (valuationLow.length() > 0) {
      int val = Integer.parseInt(valuationLow);
      this.setvaluationLow(val);
    }
  }


  /**
   * @return the percentileValue
   */
  public float getPercentileValue() {
    return percentileValue;
  }

  /**
   * @param percentileValue the percentileValue to set
   */
  public void setPercentileValue(float percentileValue) {
    this.percentileValue = percentileValue;
  }

  /**
   * @param percentileValue the percentileValue to set
   */
  public void setPercentileValue(String percentileValue) {
    this.percentileValue = Float.parseFloat(percentileValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ZPID: " + this.getZpid() + "\n");
    sb.append("Zestimate: " + this.getZestimate() + "\n");
    sb.append("Last updated: " + this.getLastUpdatedString() + "\n");
    sb.append("High Valuation: " + this.getValuationHigh() + "\n");
    sb.append("Low valuation: " + this.getvaluationLow() + "\n");
    sb.append("30 day change: " + this.getThirtyDayChange() + "\n");
    sb.append("Percentile Value: " + this.getPercentileValue() + "\n");
    sb.append("\n\n");
    return sb.toString();
  }


  @Override
  public List<DbTableObject> getDelegateObjects() {
    // TODO Auto-generated method stub
    return null;
  }
}
