package main.java.database;

import java.util.*;
import java.util.Date;

import main.java.classes_for_db.DemoGeo;
import main.java.classes_for_db.Neighborhood;
import main.java.classes_for_db.Property;
import main.java.classes_for_db.PropertyDetails;
import main.java.classes_for_db.TaxAssessment;
import main.java.classes_for_db.Zestimate;
import main.java.classes_for_db.ZillowComparable;

import java.math.BigInteger;
import java.sql.*;

/**
 * @author ronfarizon
 */

// write a procedure in SQL
public class DBReader {
    /** The name of the MySQL account to use (or empty for anonymous) */
    private final String userName = "mysqlUser";

    /** The password for the MySQL account (or empty for anonymous) */
    private final String password = "zillow";

    /** The name of the computer running MySQL */
    private final String serverName = "54.86.82.1";

    /** The port of the MySQL server (default is 3306) */
    private final int portNumber = 3306;

    /** the DB name */
    private final String dbName = "RealEstate";

    private Connection connect;

    public DBReader() {
        try {
            connect = this.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);
        String connectionString =
                "jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName;
        System.out.println(connectionString);
        conn = DriverManager.getConnection(connectionString, connectionProps);

        return conn;
    }

    /**
     * Run a SQL command which does not return a recordset: CREATE/INSERT/UPDATE/DELETE/DROP/etc.
     * 
     * @throws SQLException If something goes wrong
     */
    public boolean executeUpdate(Connection conn, String command) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(command); // This will throw a SQLException if it fails
            return true;
        } finally {

            // This will run whether we throw an exception or not
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    @SuppressWarnings("deprecation")
    public List<Property> selectProperty(String address, int zip) {
        try {
            List<Property> lp = new ArrayList<Property>();
            ResultSet rs = null;
            String st =  "SELECT * FROM Properties WHERE streetAddress LIKE ? AND zipcode = ?;";

             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

              ps.setString(1, address);
              ps.setInt(2, zip);
              
              ps.execute();
              rs = ps.executeQuery();

            
            if(rs.next() == false) {
                System.out.println("No property found for address: " + address + " and zip code: " + zip);
                return lp;
            }
            else {
                rs.beforeFirst();
                while(rs.next()) {  
                    Property p = new Property();
                    p.setZpid(BigInteger.valueOf(rs.getInt(1)));
                    p.setStreetAddress(rs.getString(2));
                    p.setZipCode(rs.getInt(3));
                    p.setCity(rs.getString(4));
                    p.setState(rs.getString(5));
                    p.setLatitude(rs.getFloat(6));
                    p.setLongitude(rs.getFloat(7));
                    p.setRegionID(rs.getInt(8));
                    p.setCountyCode(rs.getInt(9));
                    p.setUseCode(rs.getString(10));
                    p.setLotSizeSqFt(rs.getInt(11));
                    p.setFinishedSqFt(rs.getInt(12));
                    p.setBathroomCount(rs.getFloat(13));
                    p.setBedroomCount(rs.getInt(14));
                    if(rs.getInt(15) == 0) {
                        p.setLastSoldDate("");
                    }
                    else {
                    p.setLastSoldDate(rs.getDate(15));
                    }
                    p.setLastSoldPrice(rs.getInt(16));
                    if(rs.getString(17) == null) {
                        p.setYearBuilt(0);
                    }
                    else {
                        p.setYearBuilt(rs.getString(17).substring(0, 4));
                    }
                    
                    Zestimate z = selectZestimate(p.getZpid());
                    p.setZestimate(z);
                    
                    Neighborhood n = selectNeighborhood(p.getRegionID());
                    p.setNeighborhood(n);
                    
                    List<TaxAssessment> tas = selectTaxAssessments(p.getZpid());
                    p.setAssessmentList(tas);
                    
                    PropertyDetails pd = selectPropertyDetails(p.getZpid());
                    p.setPropertyDetails(pd);
                    
                    List<ZillowComparable> zcs = selectZillowComparables(p.getZpid());
                    p.setZillowComps(zcs);
                    
                    lp.add(p);

                }
                return lp;
            }   
        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select property");
            e.printStackTrace();
            return null;
        }
    }

    public Zestimate selectZestimate(BigInteger zpid) {
        try {
            Zestimate z = new Zestimate();
            ResultSet rs = null;
            String st =  "SELECT * FROM Zestimates Z WHERE zpid = ?;";

             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

              ps.setInt(1, zpid.intValue());
              ps.execute();
              rs = ps.executeQuery();
              
            if(rs.next() == false) {
                System.out.println("No Zestimate found for zpid: " + zpid);
                return z;
            }
            else {
                rs.beforeFirst();
                while(rs.next()) {
                    z.setZestimateID(BigInteger.valueOf(rs.getInt(1)));
                    z.setZpid(BigInteger.valueOf(rs.getInt(2)));
                    z.setZestimate(rs.getInt(3));
                    z.setLastUpdated(rs.getDate(4));
                    z.setThirtyDayChange(rs.getInt(5));
                    z.setValuationHigh(rs.getInt(6));
                    z.setvaluationLow(rs.getInt(7));
                    z.setPercentileValue(rs.getFloat(8));
                    z.setDateRetrieved(rs.getDate(9));
                    z.setRentZestimate(rs.getInt(10));
                    z.setRentThirtyDayChange(rs.getInt(11));
                    z.setMinRent(rs.getInt(12));
                    z.setMaxRent(rs.getInt(13));
                }

            }
            
            return z;
        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select Zestimate for zpid:" + zpid);
            e.printStackTrace();
            return null;
        }
    }
    
    public Neighborhood selectNeighborhood(int regionID) {
        try {
            Neighborhood n = new Neighborhood();
            ResultSet rs = null;
            String st =  "SELECT * FROM Neighborhoods N WHERE N.regionID = ?;";

             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

              ps.setInt(1, regionID);
              ps.execute();
              rs = ps.executeQuery();
              
            
            if(rs.next() == false) {
                System.out.println("No Neighborhood found for regionID: " + regionID);
                return n;
            }
            else {
                rs.beforeFirst();
                while(rs.next()) {
                    n.setRegionID(rs.getInt(1));
                    n.setName(rs.getString(2));
                    n.setZipCode(rs.getInt(3));
                    n.setZindex(rs.getInt(4));
                    n.setzIndexChange(rs.getFloat(5));
                    n.setType(rs.getString(6));
                }
            }
            return n;
        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select Neighborhood for regionID:" + regionID);
            e.printStackTrace();
            return null;
        }
    }
    
    public List<TaxAssessment> selectTaxAssessments(BigInteger zpid) {
        try {
            List<TaxAssessment> lt = new ArrayList<TaxAssessment>();
            ResultSet rs = null;
            String st =  "SELECT * FROM TaxAssessments T WHERE T.zpid = ?" + 
                    " ORDER BY taxYear DESC;";

             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

              ps.setInt(1, zpid.intValue());
              ps.execute();
              rs = ps.executeQuery();
              
            if(rs.next() == false) {
                System.out.println("No Tax Assessments found for zpid: " + zpid);
                return lt;
            }
            else {
                rs.beforeFirst();
                while(rs.next()) {
                    TaxAssessment t = new TaxAssessment();
                    t.setAssessmentID(BigInteger.valueOf(rs.getInt(1)));
                    t.setZpid(BigInteger.valueOf(rs.getInt(2)));
                    t.setTaxYear(rs.getInt(3));
                    t.setTaxAssessment(rs.getFloat(4));
                    lt.add(t);
                }
                return lt;
            }

        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select Tax Assessments for zpid: " + zpid);
            e.printStackTrace();
            return null;
        }
    }
    
    public PropertyDetails selectPropertyDetails(BigInteger zpid) {
        try{
            PropertyDetails pd = new PropertyDetails();
            ResultSet rs = null;
            String st =  "SELECT * FROM PropertyDetails PD WHERE PD.zpid = ?;";
            
             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

              ps.setInt(1, zpid.intValue());
              ps.execute();
              rs = ps.executeQuery();
              
              
            if(rs.next() == false) {
                System.out.println("No PropertyDetails found for zpid: " + zpid);
                return pd;
            }
            else {
                rs.beforeFirst();
                while(rs.next()) {
                    pd.setZpid(BigInteger.valueOf(rs.getInt(1)));
                    pd.setStatus(rs.getString(2));
                    pd.setPosting_type(rs.getString(3));
                    pd.setLastUpdated(rs.getDate(4));
                    if(rs.getString(5).length() < 1) {
                        pd.setyearUpdated(0);
                    }
                    else {
                    pd.setyearUpdated(rs.getString(5).substring(0,4));
                    }
                    pd.setNumFloors(rs.getInt(6));
                    pd.setBasement(rs.getString(7));
                    pd.setRoofType(rs.getString(8));
                    pd.setParkingType(rs.getString(9));
                    pd.setRooms(rs.getString(10));
                    pd.setHomeDescription(rs.getString(11));
                    pd.setNeighborhoodName(rs.getString(12));
                    pd.setSchoolDistrict(rs.getString(13));
                    pd.setPageViewThisMonth(rs.getInt(14));
                    pd.setPageViewsTotal(rs.getInt(15));
                }
                return pd;
            }
        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select Property Details for zpid:" + zpid);
            e.printStackTrace();
            return null;
        }
    }
    
    public List<ZillowComparable> selectZillowComparables(BigInteger zpid) {
        try {
            List<ZillowComparable> lc = new ArrayList<ZillowComparable>();
            ResultSet rs = null;
            String st =  "SELECT * FROM Comparables C WHERE C.primaryZPID = ?" + 
                    " ORDER BY compScore ASC;";
            
             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

              ps.setInt(1, zpid.intValue());
              ps.execute();
              rs = ps.executeQuery();
              
              
            if(!rs.next()) {
                System.out.println("No Comparables found for zpid: " + zpid);
                return lc;
            }
            else {
                rs.beforeFirst();
                while(rs.next()) {
                    ZillowComparable c = new ZillowComparable();
                    c.setCompID(BigInteger.valueOf(rs.getInt(1)));
                    c.setPrimaryZPID(BigInteger.valueOf(rs.getInt(2)));
                    c.setCompZPID(BigInteger.valueOf(rs.getInt(3)));
                    c.setCompScore(rs.getFloat(4));
                    
                    lc.add(c);
                }
                return lc;
            }

        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select Tax Assessments for zpid: " + zpid);
            e.printStackTrace();
            return null;
        }
    }
    
    public DemoGeo selectDemoGeo(int zip) {
        try {
            DemoGeo d = new DemoGeo();
            ResultSet rs = null;
            String st =  "SELECT * FROM DemoGeo D WHERE D.zip = ?;";
            
             PreparedStatement ps = this.connect.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

             ps.setInt(1, zip);
             ps.execute();
             rs = ps.executeQuery();


             if(rs.next() == false) {
                 System.out.println("No demographic info found for zip: " + zip);
                 return d;
             }
             else {
                 rs.beforeFirst();
                 while(rs.next()) {
                     d.setZipCode(rs.getInt(1));
                     d.setPop(rs.getInt(2));
                     d.setPctFemale(rs.getDouble(3));
                     d.setMedianAge(rs.getDouble(4));
                     d.setPopOver(18, rs.getInt(5));
                     d.setPctFemaleOver(18, rs.getDouble(6));
                     d.setPopOver(65, rs.getInt(7));
                     d.setPctFemaleOver(65, rs.getDouble(8));
                     d.setRacePop("white", rs.getInt(9));
                     d.setPctRacePop("white", rs.getDouble(10));
                     d.setRacePop("black", rs.getInt(11));
                     d.setPctRacePop("black", rs.getDouble(12));
                     d.setRacePop("native", rs.getInt(13));
                     d.setPctRacePop("native", rs.getDouble(14));
                     d.setRacePop("asian", rs.getInt(15));
                     d.setPctRacePop("asian", rs.getDouble(16));
                     d.setRacePop("islander", rs.getInt(17));
                     d.setPctRacePop("islander", rs.getDouble(18));
                     d.setRacePop("other", rs.getInt(19));
                     d.setPctRacePop("other", rs.getDouble(20));
                     d.setHispanicPop(rs.getInt(21));
                     d.setHispanicPctPop(rs.getDouble(22));
                     d.setPop1824(rs.getInt(23));
                     d.setPopOver(25, rs.getInt(24));
                     d.setPctEdu("less than hs", rs.getDouble(25));
                     d.setPctEdu("some hs", rs.getDouble(26));
                     d.setPctEdu("hs grads", rs.getDouble(27));
                     d.setPctEdu("some college", rs.getDouble(28));
                     d.setPctEdu("associate's degree", rs.getDouble(29));
                     d.setPctEdu("bachelor's degree", rs.getDouble(30));
                     d.setPctEdu("graduate degree", rs.getDouble(31));
                     d.setPctEduOrMore("hs", rs.getDouble(32));
                     d.setPctEduOrMore("bachelor's", rs.getDouble(33));
                     d.setMedianEarnings(rs.getInt(34));
                     d.setMedianEarningsEdu("bachelor's", rs.getInt(35));
                     d.setMedianEarningsEdu("graduate", rs.getInt(36));
                     d.setNumHouseholds(rs.getInt(37));
                     d.setFamHouseholds(rs.getInt(38));
                     d.setPctFamilyHouseholds(rs.getDouble(39));
                     d.setAvgFamSize(rs.getDouble(40));
                     d.setNumFamU18Child(rs.getInt(41));
                     d.setPctUnmarriedSex("gay", rs.getDouble(42));
                     d.setPctUnmarriedSex("straight", rs.getDouble(43));
                     d.setPctOneUnitStruct(rs.getDouble(44));
                     d.setPctTwoPlusUnitStruct(rs.getDouble(45));
                     d.setPctOcc("owner", rs.getDouble(46));
                     d.setPctOcc("renter", rs.getDouble(47));
                     d.setZipType(rs.getString(48));
                     d.setCity(rs.getString(49));
                     d.setState(rs.getString(50));
                     d.setCounty(rs.getString(51));
                     d.setTimeZone(rs.getString(52));
                     d.setAreaCode1(rs.getInt(53));
                     d.setAreaCode2(rs.getInt(54));
                     d.setLatitude(rs.getFloat(55));
                     d.setLongitude(rs.getFloat(56));
                     d.setCountry(rs.getString(57));
                 }
                 return d;
             }

        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not select DemoGeo for: " + zip);
            e.printStackTrace();
            return null;
        }
    }
}


  // /**
  // * Connect to the DB and do some stuff
  // */
  // public static void main(String[] args) {
  // DBWriter app = new DBWriter();
  // app.run();
  // }