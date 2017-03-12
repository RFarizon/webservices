/**
 * 
 */
package main.java.hello;

import java.util.ArrayList;
import java.util.List;

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
  public List<TransactionSummary> getSummary(@RequestParam(value = "table")String table, 
      @RequestParam(value = "days")int days,
      @RequestParam(value = "action")String action){
    DBReader reader = new DBReader();
    List<TransactionSummary> resultSet = new ArrayList<TransactionSummary>();
    resultSet = reader.getTransactionData(table, action, days);
    return resultSet;
  }
  
}
