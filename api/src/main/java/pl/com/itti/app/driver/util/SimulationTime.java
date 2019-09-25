package pl.com.itti.app.driver.util;

import eu.driver.adapter.constants.TopicConstants;
import eu.driver.adapter.core.CISAdapter;
import eu.driver.api.IAdaptorCallback;
import eu.driver.model.core.Timing;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.specific.SpecificData;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class SimulationTime {

    private static Timing timing = null;

    public static CISAdapter adapterInit(){
//        CISAdapter.globalConfigPath = "/opt/config";
        CISAdapter.globalConfigPath = null;
        CISAdapter adapter = CISAdapter.getInstance();
        adapter.addCallback(new CallbackValue(), TopicConstants.TIMING_TOPIC);
        return adapter;
    }

    public static LocalDateTime getTrialTime(){
        CISAdapter cisAdapter = adapterInit();
        if (timing != null) {
            return Instant.ofEpochMilli(timing.getTrialTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return cisAdapter.getTrialTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime getTimeElapsed(){
        CISAdapter cisAdapter = adapterInit();
        if (timing != null) {
            return Instant.ofEpochMilli(timing.getTimeElapsed()).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(cisAdapter.getTimeElapsed()), ZoneId.systemDefault()).toLocalTime();
    }

    public static class CallbackValue implements IAdaptorCallback {
        public CallbackValue() {
        }

        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
            if (receivedMessage.getSchema().getName().equalsIgnoreCase("Timing")) {
                try {
                    timing = (eu.driver.model.core.Timing) SpecificData.get().deepCopy(eu.driver.model.core.Timing.SCHEMA$, receivedMessage);
                } catch (Exception e) {
                    System.out.println("Error Timing receive message! " + e.getMessage());
                }
            }
        }
    }

}

