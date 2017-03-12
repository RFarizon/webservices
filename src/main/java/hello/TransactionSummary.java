/**
 * 
 */
package main.java.hello;

import java.sql.Date;

/**
 * @author brandonbogan
 *
 */
public class TransactionSummary {

  private int id;
  private String table;
  private String transactionType;
  private Date statisticDate;
  private int transactionCount;


  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the table
   */
  public String getTable() {
    return table;
  }

  /**
   * @param table the table to set
   */
  public void setTable(String table) {
    this.table = table;
  }

  /**
   * @return the transactionType
   */
  public String getTransactionType() {
    return transactionType;
  }

  /**
   * @param transactionType the transactionType to set
   */
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  /**
   * @return the statisticDate
   */
  public Date getStatisticDate() {
    return statisticDate;
  }

  /**
   * @param statisticDate the statisticDate to set
   */
  public void setStatisticDate(Date statisticDate) {
    this.statisticDate = statisticDate;
  }

  /**
   * @return the transactionCount
   */
  public int getTransactionCount() {
    return transactionCount;
  }

  /**
   * @param transactionCount the transactionCount to set
   */
  public void setTransactionCount(int transactionCount) {
    this.transactionCount = transactionCount;
  }
}
