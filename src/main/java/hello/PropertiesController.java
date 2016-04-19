package main.java.hello;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.classes_for_db.DemoGeo;
import main.java.classes_for_db.Property;
import main.java.database.DBReader;


@RestController
public class PropertiesController {
<<<<<<< HEAD
  @RequestMapping("/properties")
  public List<Property> prop(@RequestParam(value = "address") String address,
      @RequestParam(value = "zip") int zip) {
    // System.out.println("THESE ARE THE PARAMS: " + address + " AND " + zip);
    DBReader reader = new DBReader();
    List<Property> resultList = reader.selectProperty(address, zip);
    System.out
        .println("Query Complete. Number of Results Returned From DBReader: " + resultList.size());
    return resultList;
  }
}
=======
    @RequestMapping("/properties")
    public List<Property> prop(@RequestParam(value = "address")String address, @RequestParam(value = "zip")int zip) {
    	//System.out.println("THESE ARE THE PARAMS: " + address + " AND " + zip);
    	DBReader reader = new DBReader();
        return reader.selectProperty(address, zip);
    }
}
>>>>>>> master
