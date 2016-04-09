package main.java.hello;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemographicsController {



    @RequestMapping("/demographics")
    public Greeting greeting(@RequestParam(value="zip", defaultValue="00000") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}