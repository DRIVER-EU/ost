package pl.com.itti.app.driver.util;

import eu.driver.adapter.constants.TopicConstants;
import eu.driver.adapter.core.CISAdapter;
import eu.driver.examples.adapter.PrintAdapterCallback;
import org.springframework.stereotype.Component;

@Component
public class TrialStage {

    public static CISAdapter adapterInit(){
        CISAdapter.globalConfigPath = "/opt/config/";
        CISAdapter adapter = CISAdapter.getInstance();
        adapter.addCallback(new PrintAdapterCallback(), TopicConstants.TRIAL_STATE_CHANGE_TOPIC);
        return adapter;
    }

    public static Integer getOstTrialId(){
        return adapterInit().getRequestChangeOfTrialStageInfo().getOstTrialId();
    }

    public static Integer getOstTrialSessionId(){
        return adapterInit().getRequestChangeOfTrialStageInfo().getOstTrialSessionId();
    }

    public static Integer getOstTrialStageId() {
        return adapterInit().getRequestChangeOfTrialStageInfo().getOstTrialStageId();
    }
}
