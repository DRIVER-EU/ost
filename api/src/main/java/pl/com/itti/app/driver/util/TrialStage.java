package pl.com.itti.app.driver.util;

import eu.driver.adapter.constants.TopicConstants;
import eu.driver.adapter.core.CISAdapter;
import eu.driver.examples.adapter.PrintAdapterCallback;
import org.springframework.stereotype.Component;

@Component
public class TrialStage {

    public static CISAdapter adapterInit(){
        CISAdapter adapter = CISAdapter.getInstance();
        adapter.addCallback(new PrintAdapterCallback(), TopicConstants.TRIAL_STATE_CHANGE_TOPIC);
        return adapter;
    }

    public static Integer getOstTrialId(){
        return adapterInit().getRequestChangeOfTrialStageInfo().getTrialId();
    }

    public static Integer getOstTrialSessionId(){
        return adapterInit().getRequestChangeOfTrialStageInfo().getTrialSessionId();
    }

    public static Integer getOstTrialStageId() {
        return adapterInit().getRequestChangeOfTrialStageInfo().getTrialStageId();
    }
}
