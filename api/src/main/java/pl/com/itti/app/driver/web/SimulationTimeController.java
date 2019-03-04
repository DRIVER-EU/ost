package pl.com.itti.app.driver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.util.SimulationTime;

import java.time.LocalTime;
import java.util.Date;

import static pl.com.itti.app.driver.util.SimulationTime.getSimulationTime;
import static pl.com.itti.app.driver.util.SimulationTime.getTimeElapsed;

@RestController
@RequestMapping("/api")
public class SimulationTimeController {

    @GetMapping("/simulation-time")
    public String simulationTime(){
        return getSimulationTime();
    }

    @GetMapping("/time-elapsed")
    public String timeElapsed(){
        return getTimeElapsed();
    }

}
