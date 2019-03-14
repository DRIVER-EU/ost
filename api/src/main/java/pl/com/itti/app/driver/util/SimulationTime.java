package pl.com.itti.app.driver.util;

import eu.driver.adapter.constants.TopicConstants;
import eu.driver.adapter.core.CISAdapter;
import eu.driver.examples.adapter.PrintAdapterCallback;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class SimulationTime {

    public static CISAdapter adapterInit(){
        CISAdapter adapter = CISAdapter.getInstance();
        adapter.addCallback(new PrintAdapterCallback(), TopicConstants.TIMING_TOPIC);
        return adapter;
    }

    public static LocalDateTime getTrialTime(){
        return adapterInit().getTrialTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime getTimeElapsed(){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(adapterInit().getTimeElapsed()), ZoneId.systemDefault()).toLocalTime();
    }


}
