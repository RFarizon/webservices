package classes_for_db;

import java.util.List;

public interface DbTableObject {

  // /**
  // * Prepares the appropriate SQL String(s) to be used to insert this object (and relating
  // objects)
  // * into a database table. <br>
  // * <br>
  // * Some classes will return a single {@code INSERT} statement, while others will return
  // multiple,
  // * because they have nested/delegate classes that can also be written into the DB. <br>
  // * <br>
  // * For example, a {@code Property} object has a delegate Zestimate class, because search results
  // * also return Zestimates for that property. Therefore, calling this method will return a Map
  // with
  // * the SQL statements to insert both the {@code Property} object and the {@code Zestimate}
  // object.
  // * The keys of the map would be <i>'Properties'</i> and <i>'Zestimates'</i>.
  // *
  // *
  // * @return A String containing the SQL statement to insert this object into the appropriate
  // * database table.
  // */
  // public Map<Tables, String> prepareInsertStatement();

  /**
   * Many of Zillow's APIs return overlapping data. This means that data can be added or updated in
   * multiple tables, and so multiple DBTableObjects are used to keep track of this data. When
   * writing a DBTableObject to a database, it may be useful to also write the delegate objects to
   * the database. This method returns the delegate objects that should also be processed. <br>
   * <br>
   * For example, consider the Property class. This class has a Zestimate and TaxAssessment object
   * associated with it. When writing the new Property to the database, it will also be usefult to
   * write its Zestiamte and TaxAssessment.
   * 
   * @return A list of all delegate objects that the class uses. This list may be empty.
   */
  public List<DbTableObject> getDelegateObjects();
}
