/**
 * 
 */
package classes_for_db;

import java.math.BigInteger;
import java.util.List;

/**
 * @author brandonbogan
 *
 */
public class Neighborhood implements DbTableObject {

  private int regionID;
  private String name;
  private int zipCode;
  private BigInteger zpid;
  private float zIndexChange;
  private String type;


  /**
   * 
   */
  public Neighborhood() {
    // TODO Auto-generated constructor stub
  }


  /**
   * @return the regionID
   */
  public int getRegionID() {
    return regionID;
  }

  /**
   * @param regionID the regionID to set
   * @throws IllegalArgumentException if {@code regionID} falls outside of allowed range of 0 to
   *         10,000,000.
   */
  public void setRegionID(int regionID) {
    if (regionID < 0 | regionID >= 10000000) {
      throw new IllegalArgumentException(
          "Argument for regionID falls outside of allowed range of 0 to 10,000,000. Argument given: "
              + regionID);
    } else {
      this.regionID = regionID;
    }
  }

  /**
   * @param regionID the regionID to set
   * @throws IllegalArgumentException if {@code regionID} is longer than 7 characters or falls
   *         outside of allowed range of 0 to 10,000,000.
   */
  public void setRegionID(String regionID) {
    if (regionID.length() > 7) {
      throw new IllegalArgumentException(
          "Argument for regionID is longer than the maximum allowed number of characters (7). Argument given: "
              + regionID);
    } else {
      this.setRegionID(Integer.parseInt(regionID));
    }
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  public int getZipCode() {
    return zipCode;
  }


  /**
   * 
   * @param zipCode
   * @throws IllegalArgumentException if {@code zipCode} does not fall within the acceptable range
   *         of 0 <= zipCode <= 99999.
   */
  public void setZipCode(int zipCode) {
    if (zipCode < 0 | zipCode > 99999) {
      throw new IllegalArgumentException(
          "Argument for zipCode does not fall withing the acceptable range of 0 <= zipCode <= 99999. Argument given: "
              + zipCode);
    } else {
      this.zipCode = zipCode;
    }
  }

  /**
   * 
   * @param zipCode
   * @throws IllegalArgumentException if {@code zipCode} is longer than 5 characters or does not
   *         fall within the acceptable range of 0 <= zipCode <= 99999.
   */
  public void setZipCode(String zipCode) {
    if (zipCode.length() > 5) {
      throw new IllegalArgumentException(
          "Argument for zipCode exceeds the maximum length allowed of 5 characters. Argument given: "
              + zipCode);
    } else {
      this.setZipCode(Integer.parseInt(zipCode));
    }
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
   * @return the zIndexChange
   */
  public float getzIndexChange() {
    return zIndexChange;
  }

  /**
   * @param zIndexChange the zIndexChange to set
   * @throws IllegalArgumentException if {@code zIndexChange} is less than -1 or greater than 1.
   */
  public void setzIndexChange(float zIndexChange) {
    if (Math.abs(zIndexChange) > 1) {
      throw new IllegalArgumentException(
          "Invalid argument for zIndexChange. Must be between -1 and 1. Argument given: "
              + zIndexChange);
    } else {
      this.zIndexChange = zIndexChange;
    }
  }

  /**
   * @param zIndexChange the zIndexChange to set
   * @throws IllegalArgumentException if {@code zIndexChange} is longer than 6 characters, less than
   *         -1, or greater than 1.
   */
  public void setzIndexChange(String zIndexChange) {
    if (zIndexChange.length() > 6) {
      throw new IllegalArgumentException(
          "Argument for zIndexChange exceeds max character length of 6. Argument given: "
              + zIndexChange);
    } else {
      this.setzIndexChange(Float.parseFloat(zIndexChange));
    }
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }


  @Override
  public List<DbTableObject> getDelegateObjects() {
    // TODO Auto-generated method stub
    return null;
  }



}
