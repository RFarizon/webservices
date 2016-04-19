package main.java.hello;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.classes_for_db.DemoGeo;
import main.java.classes_for_db.Property;
import main.java.classes_for_db.Zestimate;
import main.java.classes_for_db.ZillowComparable;
import main.java.database.DBReader;
import main.java.dbConnectors.MysqlWriter;


@RestController
public class ZestimatesController {
    @RequestMapping(value = "/zestimate/update/value")
    public Zestimate updateZestimateValue(@RequestParam(value = "address")String address, @RequestParam(value = "zip")int zip,
    						@RequestParam(value = "valueChange", defaultValue = 0) int valueChange) {
    	DBReader reader = new DBReader();
    	MysqlWriter writer = new MysqlWriter();
    	if(reader.selectProperty(address, zip).isEmpty()) {
    		System.out.println("Unable to update zestimate; property not found at " + address + ", zip: " + zip);
    		return new Zestimate();
    	}
    	
    	Zestimate z = reader.selectProperty(address, zip).get(0).getZestimate();
    	BigInteger zpid = reader.selectProperty(address, zip).get(0).getZpid();
    	
    	writer.updateZestimateValue(z, valueChange);
    	
    	
    	System.out.println();
    	System.out.println("New zestimate: ");
    	Zestimate newZest = reader.selectZestimate(zpid);
    	return newZest;
    }
    
    @RequestMapping(value = "/zestimate/update/rent")
    public Zestimate updateRentZestimate(@RequestParam(value = "address")String address, @RequestParam(value = "zip")int zip,
    						@RequestParam(value = "rentChange", defaultValue = 0)int rentChange) {
    	DBReader reader = new DBReader();
    	MysqlWriter writer = new MysqlWriter();
    	if(reader.selectProperty(address, zip).isEmpty()) {
    		System.out.println("Unable to update rent zestimate; property not found at " + address + ", zip: " + zip);
    		return new Zestimate();
    	}
    	Zestimate z = reader.selectProperty(address, zip).get(0).getZestimate();
    	BigInteger zpid = reader.selectProperty(address, zip).get(0).getZpid();
    	
    	writer.updateRentZestimate(z, rentChange);
    	
    	
    	System.out.println();
    	System.out.println("New zestimate: ");
    	Zestimate newZest = reader.selectZestimate(zpid);
    	return newZest;
    }
}