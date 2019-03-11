package pl.com.itti.app.driver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

import static pl.com.itti.app.driver.util.SimulationTime.getTrialTime;
import static pl.com.itti.app.driver.util.SimulationTime.getTimeElapsed;

@RestController
@RequestMapping("/api")
public class SimulationTimeController {

    @GetMapping("/trial-time")
    public String trialTime(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return getTrialTime().format(dateFormat);
    }

    @GetMapping("/time-elapsed")
    public String timeElapsed(){
        return getTimeElapsed().toString();
    }

}
