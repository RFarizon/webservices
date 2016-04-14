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
			String st =  "SELECT * FROM Properties WHERE address = " + address + " AND zip = " + zip + ";";

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

					//set more stuff	

					lp.add(p);

					//
					//				private Zestimate zestimate;
					//				private boolean zestimateUsed;
					//				private Neighborhood region;
					//				private boolean regionUsed;
					//				private TaxAssessment taxAssessment;
					//				private boolean taxAssessmentUsed;
					//				private PropertyDetails details;
					//				private boolean detailsUsed;
				}
				return lp;
			}	
		}
		catch (SQLException e) {
			System.out.println("ERROR: Could not select");
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
					// date retrieved?
				}

			}
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