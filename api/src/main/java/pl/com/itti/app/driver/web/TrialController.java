package pl.com.itti.app.driver.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.AdminTrialDTO;
import pl.com.itti.app.driver.dto.ObservationTypeCriteriaDTO;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.service.ObservationTypeService;
import pl.com.itti.app.driver.service.TrialService;
import pl.com.itti.app.driver.service.TrialSessionService;
import pl.com.itti.app.driver.util.BrokerUtil;

import java.util.ArrayList;
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

    @GetMapping("/admin/ostTrials")
    public ResponseEntity getTrial(@RequestParam(value = "id") long id) {
        try{
            Trial trial = trialSessionService.getTrialById(id);
            AdminTrialDTO.FullItem adminTrialDTO = new AdminTrialDTO.FullItem();
            adminTrialDTO.toDto(trial);

            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialDTO);
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @GetMapping("/admin/ostTrialsAll")
    public ResponseEntity getAllTrials() {
        try{
            List<AdminTrialDTO.ListItem> listOfTrails = new ArrayList<>();
            Iterable<Trial> allTrials = trialService.getAllTrials();

            allTrials.forEach(item -> {
                AdminTrialDTO.ListItem adminTrialDTO = new AdminTrialDTO.ListItem();
                adminTrialDTO.toDto(item);
                listOfTrails.add(adminTrialDTO);
            });
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(listOfTrails);
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }
    @GetMapping("/admin/ostTrialsFullAll")
    public ResponseEntity getAllFullTrials() {
        try{
            List<AdminTrialDTO.FullItem> listOfTrails = new ArrayList<>();
            Iterable<Trial> allTrials = trialService.getAllTrials();

            allTrials.forEach(item -> {
                AdminTrialDTO.FullItem adminTrialDTO = new AdminTrialDTO.FullItem();
                adminTrialDTO.toDto(item);
                listOfTrails.add(adminTrialDTO);
            });
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(listOfTrails);
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @PostMapping("/admin/addNewTrial" )
    public ResponseEntity addNewTrial(@RequestBody AdminTrialDTO.ListItem adminTrialDTO) {

        try{
        Trial trial = trialService.insert(adminTrialDTO);
        adminTrialDTO.toDto(trial);

    }
     catch (Exception e)
    {
        ResponseEntity responseEntity = getResponseEntity(e);
        return responseEntity;
    }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialDTO);
        return responseEntity;
    }



    @DeleteMapping("/admin/deleteTrial")
    public ResponseEntity deleteTrial(@RequestParam(value = "id") long id) {


        try {
            Trial trial = trialService.delete(id);
            AdminTrialDTO.ListItem adminTrialDTO = new AdminTrialDTO.ListItem();
            adminTrialDTO.toDto(trial);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialDTO);
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @PutMapping("/admin/updateTrial")
    public ResponseEntity updateTrial(@RequestBody AdminTrialDTO.ListItem adminTrialDTO) {
     try {
         Trial trial = trialService.update(adminTrialDTO);
         adminTrialDTO.toDto(trial);
     }
         catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialDTO);
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

