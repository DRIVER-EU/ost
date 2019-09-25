package pl.com.itti.app.driver.util;

import eu.driver.adapter.constants.TopicConstants;
import eu.driver.adapter.core.CISAdapter;
import eu.driver.api.IAdaptorCallback;
import eu.driver.model.core.RequestChangeOfTrialStage;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.specific.SpecificData;
import org.springframework.stereotype.Component;

@Component
public class TrialStage {

    private static RequestChangeOfTrialStage requestChangeOfTrialStage = null;

    public static CISAdapter adapterInit(){
        CISAdapter.globalConfigPath = "/opt/config";
        CISAdapter adapter = CISAdapter.getInstance();
        adapter.addCallback(new CallbackValue(), TopicConstants.TRIAL_STATE_CHANGE_TOPIC);
        return adapter;
    }

    public static Integer getOstTrialId(){
        adapterInit();
        if (requestChangeOfTrialStage != null) {
            return requestChangeOfTrialStage.getOstTrialId();
        }
        return 0;
    }

    public static Integer getOstTrialSessionId(){
        adapterInit();
        if (requestChangeOfTrialStage != null) {
            return requestChangeOfTrialStage.getOstTrialSessionId();
        }
        return 0;
    }

    public static Integer getOstTrialStageId() {
        adapterInit();
        if (requestChangeOfTrialStage != null) {
            return requestChangeOfTrialStage.getOstTrialStageId();
        }
        return 0;
    }

    public static RequestChangeOfTrialStage getRequestChangeOfTrialStage() {
        adapterInit();
        return requestChangeOfTrialStage;
    }

    public static class CallbackValue implements IAdaptorCallback {
        public CallbackValue() {
        }

        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
            if (receivedMessage.getSchema().getName().equalsIgnoreCase("RequestChangeOfTrialStage")) {
                try {
                    requestChangeOfTrialStage = (eu.driver.model.core.RequestChangeOfTrialStage) SpecificData.get().deepCopy(eu.driver.model.core.RequestChangeOfTrialStage.SCHEMA$, receivedMessage);
                } catch (Exception e) {
                    System.out.println("Error RequestChangeOfTrialStage receive message! " + e.getMessage());
                }
            }
        }
    }
}
