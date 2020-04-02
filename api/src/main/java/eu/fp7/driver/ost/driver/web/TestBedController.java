package eu.fp7.driver.ost.driver.web;


import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.model.enums.SessionStatus;
import eu.fp7.driver.ost.driver.service.TrialSessionService;
import eu.fp7.driver.ost.driver.service.TrialStageService;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testbed")
public class TestBedController {

    @Autowired
    TrialSessionService trialSessionService;

    @Autowired
    TrialStageService trialStageService;


    @GetMapping("/testbed/trails")
    public JSONArray getTrialsForTestbed() {
        List<Trial> trialList = trialSessionService.findAll();
        JSONArray trialListJsonArray = new JSONArray();
        trialList.forEach(trial -> {
            JSONObject trialJsonObj = new JSONObject();
            trialJsonObj.put("name", trial.getName());
            trialJsonObj.put("id", trial.getId());

            List<TrialSession> trialSessionList = trial.getTrialSessions();
            Hibernate.initialize(trialSessionList);
            JSONArray sessionSet = new JSONArray();
            trialSessionList.forEach(session -> {
                JSONObject sessionJsonObj = new JSONObject();
                sessionJsonObj.put("id", session.getId());
                sessionJsonObj.put("status", session.getStatus().name());
                sessionJsonObj.put("currentStage", session.getLastTrialStage());
                sessionSet.put(sessionJsonObj);
            });
            trialJsonObj.put("sessionSet", sessionSet);

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
            trialListJsonArray.put(trialJsonObj);
        });
        return trialListJsonArray;
    }


    @PutMapping("/testbed/updateSession")
    public ResponseEntity updateTestbedSession(@RequestParam(value = "sessionId") long sessionId,
                                               @RequestParam(value = "sessionStatus") String sessionStatus,
                                               @RequestParam(value = "currentStageId") long currentStageId) {
        try {
            TrialSession trialSession = trialSessionService.findById(sessionId);
            TrialStage trialStage = trialStageService.findById(currentStageId);
            trialSession.setLastTrialStage(trialStage);
            if(SessionStatus.ACTIVE.name().equals(sessionStatus)){
                trialSession.setStatus(SessionStatus.ACTIVE);
            }else {
                trialSession.setStatus(SessionStatus.SUSPENDED);
            }
            trialSessionService.save(trialSession);
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("Update session successful");
        return responseEntity;
    }


    private ResponseEntity getResponseEntity(Exception e) {
        JSONObject jsonAnswer = new JSONObject();
        JSONArray serverErrorList = new JSONArray();
        JSONObject serverError = new JSONObject();
        serverError.put("serviceError", "Errors in service." + e.getMessage());
        serverErrorList.put(serverError);
        jsonAnswer.put("status", HttpStatus.BAD_REQUEST);
        jsonAnswer.put("errors", serverErrorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonAnswer.toString());
    }
}

