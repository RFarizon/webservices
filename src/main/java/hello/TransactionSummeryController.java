/**
 * 
 */
package main.java.hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.database.DBReader;

/**
 * @author brandonbogan
 *
 */
@RestController
public class TransactionSummeryController {

  @RequestMapping(value = "/monitoring/transactions/summaries")
  public List<TransactionSummary> getSummary(@RequestParam(value = "table") String table,
      @RequestParam(value = "days") int days, @RequestParam(value = "action") String action) {
    DBReader reader = new DBReader();
    List<TransactionSummary> resultSet = new ArrayList<TransactionSummary>();
    if (action.equals("CHANGES")) {
      resultSet = this.getChangeSummary(table, days, reader);
    } else if (action.equals("ADDITIONS")) {
      resultSet = this.getNetAdditionSummary(table, days, reader);
    } else {
      resultSet = reader.getTransactionData(table, action, days);
    }
    return resultSet;
  }


  private List<TransactionSummary> getChangeSummary(String table, int days, DBReader reader) {
    List<TransactionSummary> resultSet = new ArrayList<TransactionSummary>();
    List<TransactionSummary> inserts = reader.getTransactionData(table, "INSERT", days);
    List<TransactionSummary> updates = reader.getTransactionData(table, "UPDATE", days);
    List<TransactionSummary> deletes = reader.getTransactionData(table, "DELETE", days);
    Map<Date, TransactionSummary> mappedInserts = this.mapToDate(inserts);
    Map<Date, TransactionSummary> mappedUpdates = this.mapToDate(updates);
    Map<Date, TransactionSummary> mappedDeletes = this.mapToDate(deletes);
    List<Set<Date>> dateLists =
        Arrays.asList(mappedInserts.keySet(), mappedUpdates.keySet(), mappedDeletes.keySet());
    List<Date> dates = this.combineDateLists(dateLists);
    for (Date d : dates) {
      TransactionSummary combined = new TransactionSummary();
      combined.setStatisticDate(d);
      combined.setTable(table);
      combined.setTransactionType("CHANGES");
      int count = 0;
      if (mappedInserts.containsKey(d)) {
        count += mappedInserts.get(d).getTransactionCount();
      }
      if (mappedUpdates.containsKey(d)) {
        count += mappedInserts.get(d).getTransactionCount();
      }
      if (mappedDeletes.containsKey(d)) {
        count += mappedInserts.get(d).getTransactionCount();
      }
      combined.setTransactionCount(count);
      resultSet.add(combined);
    }
    return resultSet;
  }


  private List<TransactionSummary> getNetAdditionSummary(String table, int days, DBReader reader) {
    List<TransactionSummary> resultSet = new ArrayList<TransactionSummary>();
    List<TransactionSummary> inserts = reader.getTransactionData(table, "INSERT", days);
    List<TransactionSummary> deletes = reader.getTransactionData(table, "DELETE", days);
    Map<Date, TransactionSummary> mappedInserts = this.mapToDate(inserts);
    Map<Date, TransactionSummary> mappedDeletes = this.mapToDate(deletes);
    List<Set<Date>> dateLists =
        Arrays.asList(mappedInserts.keySet(), mappedDeletes.keySet());
    List<Date> dates = this.combineDateLists(dateLists);
    for (Date d : dates) {
      TransactionSummary combined = new TransactionSummary();
      combined.setStatisticDate(d);
      combined.setTable(table);
      combined.setTransactionType("ADDITIONS");
      int count = 0;
      if (mappedInserts.containsKey(d)) {
        count += mappedInserts.get(d).getTransactionCount();
      }
      if (mappedDeletes.containsKey(d)) {
        count -= mappedDeletes.get(d).getTransactionCount();
      }
      combined.setTransactionCount(count);
      resultSet.add(combined);
    }
    return resultSet;
  }


  private Map<Date, TransactionSummary> mapToDate(List<TransactionSummary> inserts) {
    Map<Date, TransactionSummary> map = new HashMap<Date, TransactionSummary>();
    for (TransactionSummary ts : inserts) {
      map.put(ts.getStatisticDate(), ts);
    }
    return map;
  }


  private List<Date> combineDateLists(List<Set<Date>> dateLists) {
    List<Date> response = new ArrayList<Date>();
    for (Set<Date> set : dateLists) {
      for (Date d : set) {
        if (!response.contains(d)) {
          response.add(d);
        }
      }
    }
    return response;
  }
}
