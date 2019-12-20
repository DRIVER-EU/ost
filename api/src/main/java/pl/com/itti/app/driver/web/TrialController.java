package pl.com.itti.app.driver.web;

import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.ObservationTypeCriteriaDTO;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.model.enums.Languages;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialRepository;
import pl.com.itti.app.driver.service.ObservationTypeService;
import pl.com.itti.app.driver.service.TrialService;
import pl.com.itti.app.driver.service.TrialSessionService;
import pl.com.itti.app.driver.util.BrokerUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrialController {

    @Autowired
    TrialSessionService trialSessionService;

    @Autowired
    TrialService trialService;

    @Autowired
    ObservationTypeService observationTypeService;

    @Autowired
    TrialRepository trialRepository;

    //TODO JKW adjust the name
    @ResponseBody
    @GetMapping("/ostAllQuestionsForMobile")
    public List<ObservationTypeDTO.SchemaItem> ostAllQuestionsForMobile(@RequestParam Long trialId, Long trialStageId, Long trialRoleId, Long trialSessionId) {
        ObservationTypeCriteriaDTO observationTypeCriteriaDTO = new ObservationTypeCriteriaDTO(trialId, trialStageId, trialRoleId, trialSessionId);
        return observationTypeService.generateSchemaList(observationTypeCriteriaDTO);
    }

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

    @GetMapping("/admin/ostTrialsAll")
    public String getAllTrials() {
        Iterable<Trial> allTrials = trialService.getAllTrials();
        JSONObject resultJsonObj = new JSONObject();
        JSONArray trialSet = new JSONArray();

        allTrials.forEach(item -> {
                JSONObject trialJsonObj = new JSONObject();
                trialJsonObj.put("id", item.getId());
                trialJsonObj.put("name", item.getName());
                trialSet.put(trialJsonObj);
         });
        resultJsonObj.put("trialsSet", trialSet);

        return resultJsonObj.toString();
    }

    @PostMapping("/admin/addNewTrial")
    public String addNewTrial(String trialName) {
        Trial trial = Trial.builder()
                .description(trialName)
                .languageVersion(Languages.ENGLISH)
                .name(trialName)
                .isDefined(true)
                .isArchived(false)
                .build();
        trialRepository.save(trial);
        return trial.toString();
    }

    @PostMapping("/admin/updateTrial")
    public String updateTrial(Long trialId ) {
        Trial trial = trialService.getTrialById(trialId);
        trial.setName("Trial Update");
        trialRepository.save(trial);
        return trial.toString();
    }

    @GetMapping("/ostTrail")
    public String getActiveSessionsAndStagesByTrialName(@RequestParam(value = "trial_name") String trialName) {
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

