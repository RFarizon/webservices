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
import main.java.classes_for_db.ZillowComparable;
import main.java.database.DBReader;
import main.java.dbConnectors.MysqlWriter;


@RestController
public class ComparablesController {
    @RequestMapping(value = "/comparables/insert")
    public List<ZillowComparable> CreateComp(@RequestParam(value = "address1")String address1, @RequestParam(value = "zip1")int zip1,
    						@RequestParam (value = "address2") String address2, @RequestParam (value = "zip2")int zip2, float compScore) {
    	DBReader reader = new DBReader();
    	MysqlWriter writer = new MysqlWriter();
    	if(reader.selectProperty(address1, zip1).isEmpty()) {
    		System.out.println("Unable to create comp; property not found at " + address1 + ", zip: " + zip1);
    		return new ArrayList<ZillowComparable>();
    	}
    	if(reader.selectProperty(address2, zip2).isEmpty()) {
    		System.out.println("Unable to create comp; property not found at " + address2 + ", zip: " + zip2);
    		return new ArrayList<ZillowComparable>();
    	}
    	BigInteger zpid1 = reader.selectProperty(address1, zip1).get(0).getZpid();
    	BigInteger zpid2 = reader.selectProperty(address2, zip2).get(0).getZpid();
    	
    	ZillowComparable zc = new ZillowComparable();
    	zc.setPrimaryZPID(zpid1);
    	zc.setCompZPID(zpid2);
    	zc.setCompScore(compScore);
    	zc.setCompAddress(address2);
    	zc.setCompZip(zip2);
    	
    	writer.insertObject(zc);
    	
    	System.out.println();
    	System.out.println("New comp list: ");
    	List<ZillowComparable> compList = reader.selectZillowComparables(zpid1);
    	return compList;
    }
    
    @RequestMapping(value = "/comparables/delete")
    public List<ZillowComparable> DeleteComp(@RequestParam(value = "address1")String address1, @RequestParam(value = "zip1")int zip1,
			@RequestParam(value = "address2")String address2, @RequestParam(value = "zip2")int zip2, float compScore) {
    	DBReader reader = new DBReader();
    	MysqlWriter writer = new MysqlWriter();
    	if(reader.selectProperty(address1, zip1).isEmpty()) {
    		System.out.println("Unable to delete comp; property not found at " + address1 + ", zip: " + zip1);
    		return new ArrayList<ZillowComparable>();
    	}
    	if(reader.selectProperty(address2, zip2).isEmpty()) {
    		System.out.println("Unable to delete comp; property not found at " + address2 + ", zip: " + zip2);
    		return new ArrayList<ZillowComparable>();
    	}
    	BigInteger zpid1 = reader.selectProperty(address1, zip1).get(0).getZpid();
    	BigInteger zpid2 = reader.selectProperty(address2, zip2).get(0).getZpid();
    	
    	ZillowComparable zc = new ZillowComparable();
    	zc.setPrimaryZPID(zpid1);
    	zc.setCompZPID(zpid2);
    	zc.setCompScore(compScore);
    	zc.setCompAddress(address2);
    	zc.setCompZip(zip2);
    	
    	writer.deleteObject(zc);
    	
    	System.out.println();
    	System.out.println("New comp list: ");
    	List<ZillowComparable> compList = reader.selectZillowComparables(zpid1);
    	return compList;
    }
}