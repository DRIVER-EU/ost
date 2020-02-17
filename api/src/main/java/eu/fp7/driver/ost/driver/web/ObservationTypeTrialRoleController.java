package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRoleId;
import eu.fp7.driver.ost.driver.service.ObservationTypeTrialRoleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/observationtypestrialrole")
public class ObservationTypeTrialRoleController {

    @Autowired
    private ObservationTypeTrialRoleService observationTypeTrialRoleService;



    @PostMapping("/admin/addNewObservationTypeTrailRole" )

    public ResponseEntity insert(@RequestBody ObservationTypeTrialRoleId observationTypeTrialRoleId) {
        long observationTypeId = observationTypeTrialRoleId.getObservationTypeId();
        long trialRoleId= observationTypeTrialRoleId.getTrialRoleId();
        try{
            observationTypeTrialRoleService.insert(trialRoleId,observationTypeId);
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("ObservationTypeTrailRole(roleId,observationTypeId) = (" + trialRoleId +","+observationTypeId+ ") was added");
        return responseEntity;
    }


    @DeleteMapping("/admin/deleteObservationTypeTrailRole")
    public ResponseEntity delete(@RequestParam(value = "trialRoleId") long trialRoleId, @RequestParam(value = "observationTypeId") long observationTypeId) {
        try {
            observationTypeTrialRoleService.delete(trialRoleId, observationTypeId);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("ObservationTypeTrailRole (roleId,observationTypeId) = (" + trialRoleId +","+observationTypeId+ ") is deleted");
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

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
