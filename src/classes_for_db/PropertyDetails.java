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
 * @author brandonbogan
 *
 */
public class PropertyDetails implements DbTableObject {
  // zpid INT(10)
  // status VARCHAR(15)
  // posting_type VARCHAR(45)
  // last_updated_date DATE()
  // year_updated YEAR()
  // number_floors INT(2)
  // basement VARCHAR(25)
  // roof_type VARCHAR(25)
  // parking_type VARCHAR(25)
  // rooms TEXT()
  // home_description TEXT()
  // neighborhood_name VARCHAR(100)
  // school_district VARCHAR(100)
  // page_views_this_month INT(8)
  // page_vies_total INT(8)
  private BigInteger zpid;
  private String status;
  private String posting_type;
  private Date lastUpdated;
  private int yearUpdated;
  private int numFloors;
  private String basement;
  private String roofType;
  private String parkingType;
  private String rooms;
  private int numRooms;
  private String homeDescription;
  private String neighborhoodName;
  private String schoolDistrict;
  private int pageViewThisMonth;
  private int pageViewsTotal;


  /**
   * 
   */
  public PropertyDetails() {

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
   * @return the status
   */
  public String getStatus() {
    return status;
  }


  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }


  /**
   * @return the posting_type
   */
  public String getPosting_type() {
    return posting_type;
  }


  /**
   * @param posting_type the posting_type to set
   */
  public void setPosting_type(String posting_type) {
    this.posting_type = posting_type;
  }


  /**
   * @return the date the property was last updated
   */
  public Date getLastUpdated() {
    return lastUpdated;
  }

  /**
   * @return the date the property was last updated
   */
  public Date getLastUpdatedString() {
    String response = "";
    if (this.lastUpdated != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS:d");
      response.concat(sdf.format(this.lastUpdated));
    }
    return lastUpdated;
  }

  /**
   * @param lastUpdated the date the property was last updated
   */
  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @param lastUpdated the date the property was last updated
   */
  public void setLastUpdated(String lastUpdated) {
    Date date = null;
    if (lastUpdated != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS:d");
      try {
        date = sdf.parse(lastUpdated);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    // try {
    // date = formatter.parse(lastUpdated);
    // } catch (ParseException e) {
    // e.printStackTrace();
    // System.out.println("Error converting String to Date: " + lastUpdated);
    // }
    this.setLastUpdated(date);
  }

  /**
   * @return the yearUpdated
   */
  public int getyearUpdated() {
    return yearUpdated;
  }


  /**
   * @param yearUpdated the yearUpdated to set
   * @throws IllegalArgumentException if {@code yearUpdated} is less than 1900 or greater than 2017
   */
  public void setyearUpdated(int yearUpdated) {
    if (yearUpdated < 1900 | yearUpdated > 2017) {
      throw new IllegalArgumentException(
          "Argument for yearUpdated does not fall within allowed range of 1900 <= yearUpdated <= 2017. Argument given: "
              + yearUpdated);
    }
    this.yearUpdated = yearUpdated;
  }

  /**
   * @param yearUpdated the yearUpdated to set
   * @throws IllegalArgumentException if {@code yearUpdated} is less than 1900 or greater than 2017
   */
  public void setyearUpdated(String yearUpdated) {
    if (yearUpdated != null && yearUpdated.length() > 4) {
      throw new IllegalArgumentException(
          "Argument for yearUpdated exceeds max allowed length of 4 characters. Argument given: "
              + yearUpdated);
    } else if (yearUpdated != null) {
      this.setyearUpdated(Integer.parseInt(yearUpdated));
    } else {
      this.yearUpdated = 0;
    }
  }


  /**
   * @return the number of floors the property has
   */
  public int getNumFloors() {
    return numFloors;
  }


  /**
   * @param numFloors the number of floors the property has
   * @throws IllegalArgumentException if {@code numFloors} falls outside of the allowed range of 0
   *         through 999.
   */
  public void setNumFloors(int numFloors) {
    if (numFloors < 0 | numFloors > 999) {
      throw new IllegalArgumentException(
          "Argument given for numFloors falls outside of the allowed range of 0 through 999. Argument given: "
              + numFloors);
    } else {
      this.numFloors = numFloors;
    }
  }

  /**
   * @param numFloors the number of floors the property has
   * @throws IllegalArgumentException if {@code numFloors} is longer than 3 characters or falls
   *         outside of the allowed range of 0 through 999.
   */
  public void setNumFloors(String numFloors) {
    if (numFloors != null) {
      if (numFloors.length() > 3) {
        throw new IllegalArgumentException(
            "Argument for numFloors exceeds max number of characters (3). Argument given: "
                + numFloors);
      } else {
        this.setNumFloors(Integer.parseInt(numFloors));
      }
    }
  }


  /**
   * @return the basement
   */
  public String getBasement() {
    return basement;
  }


  /**
   * @param basement the basement to set
   */
  public void setBasement(String basement) {
    this.basement = basement;
  }


  /**
   * @return the roofType
   */
  public String getRoofType() {
    return roofType;
  }


  /**
   * @param roofType the roofType to set
   */
  public void setRoofType(String roofType) {
    this.roofType = roofType;
  }


  /**
   * @return the parkingType
   */
  public String getParkingType() {
    return parkingType;
  }


  /**
   * @param parkingType the parkingType to set
   */
  public void setParkingType(String parkingType) {
    this.parkingType = parkingType;
  }


  /**
   * @return the rooms
   */
  public String getRooms() {
    return rooms;
  }


  /**
   * @param rooms the rooms to set
   */
  public void setRooms(String rooms) {
    this.rooms = rooms;
  }


  /**
   * @return the numRooms
   */
  public int getNumRooms() {
    return numRooms;
  }


  /**
   * @param numRooms the numRooms to set
   * @throws IllegalArgumentException if {@code numRooms} is less than 0
   */
  public void setNumRooms(int numRooms) {
    if (numRooms < 0) {
      throw new IllegalArgumentException(
          "Argument for number of rooms cannot be less than 0. Argument given: " + numRooms);
    } else {
      this.numRooms = numRooms;
    }
  }

  /**
   * @param numRooms the numRooms to set
   * @throws IllegalArgumentException if {@code numRooms} is less than 0
   */
  public void setNumRooms(String numRooms) {
    this.setNumRooms(Integer.parseInt(numRooms));
  }

  /**
   * @return the homeDescription
   */
  public String getHomeDescription() {
    return homeDescription;
  }


  /**
   * @param homeDescription the homeDescription to set
   */
  public void setHomeDescription(String homeDescription) {
    this.homeDescription = homeDescription;
  }


  /**
   * @return the neighborhoodName
   */
  public String getNeighborhoodName() {
    return neighborhoodName;
  }


  /**
   * @param neighborhoodName the neighborhoodName to set
   */
  public void setNeighborhoodName(String neighborhoodName) {
    this.neighborhoodName = neighborhoodName;
  }


  /**
   * @return the schoolDistrict
   */
  public String getSchoolDistrict() {
    return schoolDistrict;
  }


  /**
   * @param schoolDistrict the schoolDistrict to set
   */
  public void setSchoolDistrict(String schoolDistrict) {
    this.schoolDistrict = schoolDistrict;
  }


  /**
   * @return the number of times the property has been viewed this month
   */
  public int getPageViewThisMonth() {
    return pageViewThisMonth;
  }


  /**
   * @param pageViewThisMonth the number of times the property has been viewed this month
   */
  public void setPageViewThisMonth(int pageViewThisMonth) {
    this.pageViewThisMonth = pageViewThisMonth;
  }

  /**
   * @param pageViewThisMonth the number of times the property has been viewed this month
   */
  public void setPageViewThisMonth(String pageViewThisMonth) {
    this.setPageViewThisMonth(Integer.parseInt(pageViewThisMonth));
  }


  /**
   * @return the total number of times the property has been viewed on Zillow
   */
  public int getPageViewsTotal() {
    return pageViewsTotal;
  }


  /**
   * @param pageViewsTotal the total number of times the property has been viewed on Zillow
   */
  public void setPageViewsTotal(int pageViewsTotal) {
    this.pageViewsTotal = pageViewsTotal;
  }

  /**
   * @param pageViewsTotal the total number of times the property has been viewed on Zillow
   */
  public void setPageViewsTotal(String pageViewsTotal) {
    this.setPageViewsTotal(Integer.parseInt(pageViewsTotal));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ZPID: " + this.getZpid() + "\n");
    sb.append("Views this month: " + this.getPageViewThisMonth() + "\n");
    sb.append("Total views: " + this.getPageViewsTotal() + "\n");
    sb.append("Properrty status: " + this.getStatus() + "\n");
    sb.append("Posting Type: " + this.getPosting_type() + "\n");
    sb.append("Date last updated: " + this.getLastUpdatedString() + "\n");
    sb.append("Year Updated: " + this.getyearUpdated() + "\n");
    sb.append("Number of floors: " + this.getNumFloors() + "\n");
    sb.append("Basement type: " + this.getBasement() + "\n");
    sb.append("Roof type: " + this.getRoofType() + "\n");
    sb.append("Parking Type: " + this.getParkingType() + "\n");
    sb.append("Room types: " + this.getRooms() + "\n");
    sb.append("Room count: " + this.getNumRooms() + "\n");
    sb.append("Home Description: " + this.getHomeDescription() + "\n");
    sb.append("Neighborhood Name: " + this.getNeighborhoodName() + "\n");
    sb.append("School District: " + this.getSchoolDistrict() + "\n");
    sb.append("\n\n");
    return sb.toString();
  }


  @Override
  public List<DbTableObject> getDelegateObjects() {
    // TODO Auto-generated method stub
    return null;
  }

}
