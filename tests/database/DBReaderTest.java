/**
 * 
 */
package database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import classes_for_db.Property;

/**
 * @author brandonbogan
 *
 */
public class DBReaderTest {

  /**
   * Test method for {@link database.DBReader#getConnection()}.
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
   * Test method for {@link database.DBReader#selectProperty(java.lang.String, int)}.
   */
  @Test
  public void testSelectProperty() {
    DBReader reader = new DBReader();
    List<Property> testPropList = reader.selectProperty("580 Washington St", 02111);
    // Test to see that the list has 5 properties in it, which is the number or properties that are
    // in the Properties table with the same address and zip
    assertEquals(5, testPropList.size());
    // Check that the list is actually populated with Properties, and not nulls
    for(Property p : testPropList){
      assertNotNull(p);
    }
    
  }

  /**
   * Test method for {@link database.DBReader#selectZestimate(java.math.BigInteger)}.
   */
  @Test
  public void testSelectZestimate() {
    fail("Not yet implemented"); // TODO
  }

}
