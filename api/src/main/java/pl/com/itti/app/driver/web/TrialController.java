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
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.service.TrialSessionService;

import java.util.List;

import static pl.com.itti.app.driver.util.TrialStage.getOstTrialId;
import static pl.com.itti.app.driver.util.TrialStage.getOstTrialSessionId;
import static pl.com.itti.app.driver.util.TrialStage.getOstTrialStageId;

@RestController
@RequestMapping("/api")
public class TrialController {

    @Autowired
    TrialSessionService trialSessionService;

    @GetMapping("/ostTrialId")
    public Integer ostTrialId() {
        return getOstTrialId();
    }

    @GetMapping("/ostTrialSessionId")
    public Integer timeElapsed() {

        return getOstTrialSessionId();
    }

    @GetMapping("/ostTrialStageId")
    public Integer ostTrialStageId() {
        return getOstTrialStageId();
    }


    @GetMapping("/ostTrail")
    public String sayPlainTextHello(@RequestParam(value = "trial_name") String trialName) {
        Trial trial = trialSessionService.getTrialByName(trialName);

        JSONObject trialJsonObj = new JSONObject();
        trialJsonObj.put("name", trial.getName());
        trialJsonObj.put("id", trial.getId());

        List<TrialSession> trialSessionList = trial.getTrialSessions();
        Hibernate.initialize(trialSessionList);
        JSONArray sessionSet = new JSONArray();
        trialSessionList.forEach(item -> {
            if(item.getStatus()==SessionStatus.ACTIVE) {
                sessionSet.put(item.getId());
            }
        });
        trialJsonObj.put("sessionIdSet", sessionSet);

        JSONArray stageSet = new JSONArray();
        List<TrialStage> trialStageList = trial.getTrialStages();
        Hibernate.initialize(trialStageList);
        trialStageList.forEach(item -> {
            JSONObject stageJsonObj = new JSONObject();
            stageJsonObj.put("id", item.getId());
            stageJsonObj.put("name", item.getName());
            stageSet.put(stageJsonObj);
        });
        trialJsonObj.put("stageSet", stageSet);

        return trialJsonObj.toString();
    }
}

