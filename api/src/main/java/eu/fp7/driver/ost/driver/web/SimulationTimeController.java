//package eu.fp7.driver.ost.driver.web;
//
//import eu.fp7.driver.ost.driver.util.BrokerUtil;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.format.DateTimeFormatter;
//
//
//@RestController
//@RequestMapping("/api")
//public class SimulationTimeController {
//
//    @GetMapping("/trial-time")
//    public String trialTime(){
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        return BrokerUtil.getTrialTime().format(dateFormat);
//    }
//
//    @GetMapping("/time-elapsed")
//    public String timeElapsed(){
//        return BrokerUtil.getTimeElapsed().toString();
//    }
//
//}
