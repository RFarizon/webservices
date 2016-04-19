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
  @RequestMapping("/properties")
  public List<Property> prop(@RequestParam String address, @RequestParam int zip) {
    DBReader reader = new DBReader();
    return reader.selectProperty(address, zip);
  }
}
