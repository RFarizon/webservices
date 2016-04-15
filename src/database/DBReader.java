package database;

import java.util.*;
import java.util.Date;

import classes_for_db.Neighborhood;
import classes_for_db.Property;
import classes_for_db.PropertyDetails;
import classes_for_db.TaxAssessment;
import classes_for_db.Zestimate;
import classes_for_db.ZillowComparable;

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
			this.getConnection();
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
		connect = conn;

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


	public List<Property> selectProperty(String address, int zip) {
		try {
			List<Property> lp = new ArrayList<Property>();
			ResultSet rs = null;
			String st =  "SELECT * FROM Properties WHERE address LIKE " + address + " AND zip = " + zip + ";";

			rs = connect.createStatement().executeQuery(st);
			if(rs.next() == false) {
				System.out.println("No property found for address: " + address + " and zip code: " + zip);
				return lp;
			}
			else {
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
					p.setYearBuilt(rs.getString(11));
					p.setLotSizeSqFt(rs.getInt(12));
					p.setFinishedSqFt(rs.getInt(13));
					p.setBathroomCount(rs.getFloat(14));
					p.setBedroomCount(rs.getInt(15));
					p.setLastSoldDate(rs.getDate(16));
					p.setLastSoldPrice(rs.getInt(17));
					
					
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
			Zestimate z = null;
			ResultSet rs = null;
			String st =  "SELECT * FROM Zestimates Z WHERE Z.zpid = " + zpid + ";";
			rs = connect.createStatement().executeQuery(st);
			if(rs.next() == false) {
				System.out.println("No Zestimate found for zpid: " + zpid);
				return z;
			}
			else {
				while(rs.next()) {
					z.setZestimate(rs.getInt(1));
					z.setZpid(BigInteger.valueOf(rs.getInt(2)));
					z.setLastUpdated(rs.getDate(3));
					z.setThirtyDayChange(rs.getInt(4));
					z.setValuationHigh(rs.getInt(5));
					z.setvaluationLow(rs.getInt(6));
					z.setPercentileValue(rs.getFloat(7));
					z.setDateRetrieved(rs.getDate(8));
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
			Neighborhood n = null;
			ResultSet rs = null;
			String st =  "SELECT * FROM Neighborhoods N WHERE N.regionID = " + regionID + ";";
			rs = connect.createStatement().executeQuery(st);
			if(rs.next() == false) {
				System.out.println("No Neighborhood found for regionID: " + regionID);
				return n;
			}
			else {
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
			String st =  "SELECT * FROM TaxAssessments T WHERE T.zpid = " + zpid + 
					"ORDER BY taxYear DESC"+ ";";
			rs = connect.createStatement().executeQuery(st);
			if(rs.next() == false) {
				System.out.println("No Tax Assessments found for zpid: " + zpid);
				return lt;
			}
			else {
				while(rs.next()) {
					TaxAssessment t = null;
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
			PropertyDetails pd = null;
			ResultSet rs = null;
			String st =  "SELECT * FROM PropertyDetails PD WHERE PD.zpid = " + zpid + ";";
			rs = connect.createStatement().executeQuery(st);
			if(rs.next() == false) {
				System.out.println("No PropertyDetails found for zpid: " + zpid);
				return pd;
			}
			else {
				while(rs.next()) {
					pd.setZpid(BigInteger.valueOf(rs.getInt(1)));
					pd.setStatus(rs.getString(2));
					pd.setPosting_type(rs.getString(3));
					pd.setLastUpdated(rs.getDate(4));
					pd.setyearUpdated(rs.getString(5));
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
			String st =  "SELECT * FROM Comparable C WHERE C.primaryZPID = " + zpid + 
					"ORDER BY compScore ASC"+ ";";
			rs = connect.createStatement().executeQuery(st);
			if(rs.next() == false) {
				System.out.println("No Comparables found for zpid: " + zpid);
				return lc;
			}
			else {
				while(rs.next()) {
					ZillowComparable c = null;
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
}


  // /**
  // * Connect to the DB and do some stuff
  // */
  // public static void main(String[] args) {
  // DBWriter app = new DBWriter();
  // app.run();
  // }