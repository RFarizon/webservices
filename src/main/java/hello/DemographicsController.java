package main.java.hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.classes_for_db.DemoGeo;
import main.java.database.DBReader;


@RestController
public class DemographicsController {
    @RequestMapping("/demographics")
    public DemoGeo dem(@RequestParam(value="zip", defaultValue="00000") int zip) {
    	DBReader reader = new DBReader();
        return reader.selectDemoGeo(zip);
    }
}