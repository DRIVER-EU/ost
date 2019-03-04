package pl.com.itti.app.driver.util;

import eu.driver.adapter.core.CISAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SimulationTime {

    private static CISAdapter adapter = CISAdapter.getInstance();

    public static String getSimulationTime(){

        LocalDateTime date = adapter.getTrialTime().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return date.format(dateFormat);
    }

    public static String getTimeElapsed(){
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(adapter.getTimeElapsed()), ZoneId.of("UTC"));
        return date.toLocalTime().toString();
    }
}
