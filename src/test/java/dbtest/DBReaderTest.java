package test.java.dbtest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.classes_for_db.DemoGeo;
import main.java.classes_for_db.Neighborhood;
import main.java.classes_for_db.Property;
import main.java.classes_for_db.TaxAssessment;
import main.java.classes_for_db.Zestimate;
import main.java.classes_for_db.ZillowComparable;
import main.java.database.DBReader;
import main.java.hello.TransactionSummary;

public class DBReaderTest {


	  /**
	   * Test method for {@link main.java.database.DBReader#getConnection()}.
	   */
	  @Test
	  public void testGetConnection() {
	    DBReader reader = new DBReader();
	    Connection conn = null;
	    try {
	      conn = reader.getConnection();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    // Here is where the actual test occurs
	    assertNotNull(conn);
	  }

	  /**
	   * Test method for {@link main.java.database.DBReader#selectProperty(java.lang.String, int)}.
	   */
	  @Test
	  public void testSelectProperty() {
	    DBReader reader = new DBReader();
	    List<Property> testPropList = reader.selectProperty("26 Old Colony Dr", 1581);
	    
	    // Test to see that the list has 5 properties in it, which is the number or properties that are
	    // in the Properties table with the same address and zip
	    assertNotNull(testPropList.get(0));
	    assertEquals(1581, testPropList.get(0).getZipCode());
	    
	    /*
	     *  Check for the following:
	     *  1.) that the list is actually populated with Properties, and not nulls, 
	     *  2.) that all the Properties have zpids.
	     *  3.) That the addresses match what we queried for 
	     *  4.) That the zip codes match what we queried for  
	     */
//	    for(Property p : testPropList){
//	      assertNotNull(p);
//	      assertNotNull(p.getZpid());
//	      assertEquals(true, p.getStreetAddress().equals("580 Washington St"));
//	      assertEquals(2111, p.getZipCode());
//	    }
	//    
//	    assertEquals(1162, testPropList.get(0).getFinishedSqFt());
//	    assertEquals(0, testPropList.get(2).getYearBuilt());

	  }

	  /**
	   * Test method for {@link main.java.database.DBReader#selectZestimate(java.math.BigInteger)}.
	   */
	  @Test
	  public void testSelectZestimate() {
		  DBReader reader = new DBReader();
		  List<Property> testPropList = reader.selectProperty("580 Washington St", 2111);
		  ArrayList<Zestimate> testZests = new ArrayList<Zestimate>();
		  for(Property p : testPropList) {
			  testZests.add(reader.selectZestimate(p.getZpid()));
		  }
		  for(Zestimate z : testZests) {
			  assertNotNull(z);
		      assertNotNull(z.getZpid());
		      assertNotNull(z.getZestimateID());
		  }
		 assertNotNull(testZests.get(4).getThirtyDayChange());
		 // assertEquals(3450024, testZests.get(15).getZestimate());
		  assertEquals(6, testZests.size());
		//  assertEquals(1333353, testZests.get(25).getvaluationLow());
		  
	  }
	  @Test
	  public void testSelectNeighborhood() {
		  DBReader reader = new DBReader();
		  List<Property> testPropList = reader.selectProperty("580 Washington St", 2111);
		  ArrayList<Neighborhood> testHoods = new ArrayList<Neighborhood>();
		  for(Property p : testPropList) {
			  testHoods.add(reader.selectNeighborhood(p.getRegionID()));
		  }
		  for(Neighborhood n : testHoods) {
			  assertNotNull(n);
		      assertNotNull(n.getRegionID());
		      assertNotNull(n.getZindex());
		  }
		 assertEquals(true, testHoods.get(4).getType().equals("neighborhood"));
		 // assertEquals(3450024, testZests.get(15).getZestimate());
		  assertEquals(6, testHoods.size());
	  }
	  
	  @Test
	  public void testSelectTaxAssessments() {
		  DBReader reader = new DBReader();
		  List<Property> testPropList = reader.selectProperty("580 Washington St", 2111);
		  List<TaxAssessment> testTax = reader.selectTaxAssessments(testPropList.get(0).getZpid());
		  assertEquals(true, !testTax.isEmpty());
		  assertNotNull(testTax.get(0).getAssessmentID());
		  assertEquals(2015, testTax.get(0).getTaxYear());
	  }
	  
	  @Test
	  public void testSelectComparables() {
		  DBReader reader = new DBReader();
		  List<Property> testPropList = reader.selectProperty("580 Washington St", 2111);
		  List<ZillowComparable> testComps = testPropList.get(0).getZillowComps();
		  assertTrue(testComps.size() > 0);
	  }
	  
	  @Test
	  public void testSelectDemo() {
		  DBReader reader = new DBReader();
		  DemoGeo dallas = reader.selectDemoGeo(75201);
		  assertEquals(20, dallas.getRacePctPop("black"), 0.5);
	  }
	  
	  @Test
	  public void testGetTransactionSummary(){
	    DBReader reader = new DBReader();
	    List<TransactionSummary> testResult = reader.getTransactionData("Addresses", "INSERT", 1);
	    assertEquals(1, testResult.size());
	    assertEquals("Addresses", testResult.get(0).getTable());
	    assertEquals(288, testResult.get(0).getTransactionCount());
	  }


}
