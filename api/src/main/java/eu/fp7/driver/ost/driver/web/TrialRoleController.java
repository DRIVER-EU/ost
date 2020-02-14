package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.dto.PageDto;
import eu.fp7.driver.ost.driver.dto.TrialRoleDTO;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.service.ObservationTypeService;
import eu.fp7.driver.ost.driver.service.TrialRoleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class TrialRoleController {

    @Autowired
    private TrialRoleService trialRoleService;

    @Autowired
    private ObservationTypeService observationTypeService;


    @GetMapping
    private PageDto<TrialRoleDTO.FullItem> findByTrialId(
            @RequestParam(value = "trial_id") long trialId, Pageable pageable) {
        return Dto.from(trialRoleService.findByTrialId(trialId, pageable), TrialRoleDTO.FullItem.class);
    }

    @PostMapping("/admin/addNewTrialRole" )
    public ResponseEntity addNewTrialRole(@RequestBody TrialRoleDTO.FullItem adminTrialDTO) {

        try{
            TrialRole trialRole = trialRoleService.insert(adminTrialDTO);
            adminTrialDTO.toDto(trialRole);

        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialDTO);
        return responseEntity;
    }



    @DeleteMapping("/admin/deleteTrialRole")
    public ResponseEntity deleteTrial(@RequestParam(value = "id") long id) {


        try {
            trialRoleService.delete(id);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("TrialRole  id =" + id + " is deleted");
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @PutMapping("/admin/updateTrialRole")
    public ResponseEntity updateTrial(@RequestBody TrialRoleDTO.FullItem fullItem) {
        try {
            TrialRole trialRole = trialRoleService.update(fullItem);
            fullItem.toDto(trialRole);
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(fullItem);
        return responseEntity;
    }


    @GetMapping("/admin/getFullTrialRole")
    private ResponseEntity getFullQuestion(@RequestParam(value = "id") long id){


        TrialRoleDTO.FullItem fullItem = new TrialRoleDTO.FullItem();
        try{
            TrialRole trialRole = trialRoleService.findById(id);
            List<ObservationType> unassignedObservationTypes = observationTypeService.getAllObservationTypesForTrial(trialRole.getTrial().getId());
            fullItem.toDto(trialRole, unassignedObservationTypes);
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(fullItem);
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
