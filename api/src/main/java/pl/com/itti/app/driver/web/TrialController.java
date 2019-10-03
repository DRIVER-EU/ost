package pl.com.itti.app.driver.web;

import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.util.BrokerUtil;

@RestController
@RequestMapping("/api")
public class TrialController {

    @Autowired
    TrialSessionService trialSessionService;

    @GetMapping("/ostTrialId")
    public Long ostTrialId() {
        return BrokerUtil.trialId;
    }

    @GetMapping("/ostTrialSessionId")
    public Long timeElapsed() {
        return BrokerUtil.trialSessionId;
    }

    @GetMapping("/ostTrialStageId")
    public Long ostTrialStageId(){
        return BrokerUtil.trialStageId;
    }


    @GetMapping("/ostTrail")
    public String sayPlainTextHello(@RequestParam(value = "trial_name") String trialName) {
        Trial trial = trialSessionService.getTrialByName(trialName);
        if(trial==null) return "no trial found";
        JSONObject trialJsonObj = new JSONObject();
        trialJsonObj.put("name", trial.getName());
        trialJsonObj.put("id", trial.getId());

        List<TrialSession> trialSessionList = trial.getTrialSessions();
        JSONArray sessionSet = new JSONArray();
        if(trialSessionList!=null) {
            Hibernate.initialize(trialSessionList);

            trialSessionList.forEach(item -> {
                if (item.getStatus() == SessionStatus.ACTIVE && item.getIsManualStageChange()!=null &&!item.getIsManualStageChange()) {
                    sessionSet.put(item.getId());
                }
            });
        }
        trialJsonObj.put("sessionIdSet", sessionSet);

        JSONArray stageSet = new JSONArray();
        List<TrialStage> trialStageList = trial.getTrialStages();
        if(trialStageList!=null) {
            Hibernate.initialize(trialStageList);
            trialStageList.forEach(item -> {
                JSONObject stageJsonObj = new JSONObject();
                stageJsonObj.put("id", item.getId());
                stageJsonObj.put("name", item.getName());
                stageSet.put(stageJsonObj);
            });
        }
        trialJsonObj.put("stageSet", stageSet);

        return trialJsonObj.toString();
    }
}

