package pl.com.itti.app.driver.util;

import eu.driver.adapter.core.CISAdapter;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class SimulationTime {

    public static CISAdapter adapterInit(){
        return CISAdapter.getInstance();
    }

    public static LocalDateTime getSimulationTime(){
        return adapterInit().getTrialTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime getTimeElapsed(){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(adapterInit().getTimeElapsed()), ZoneId.systemDefault()).toLocalTime();
    }
}
