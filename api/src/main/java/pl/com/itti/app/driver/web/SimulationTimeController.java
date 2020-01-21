package pl.com.itti.app.driver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.util.BrokerUtil;

import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/api")
public class SimulationTimeController {

    @GetMapping("/trial-time")
    public String trialTime(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return BrokerUtil.getTrialTime().format(dateFormat);
    }

    @GetMapping("/time-elapsed")
    public String timeElapsed(){
        return BrokerUtil.getTimeElapsed().toString();
    }

}
