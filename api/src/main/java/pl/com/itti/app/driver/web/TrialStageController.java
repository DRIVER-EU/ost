package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.AdminTrialStageDTO;
import pl.com.itti.app.driver.dto.TrialStageDTO;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.service.TrialStageService;
import pl.com.itti.app.core.annotation.FindAllGetMapping;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.PageDto;

@RestController
@RequestMapping("api/stages")
public class TrialStageController {

    @Autowired
    private TrialStageService trialStageService;


    @FindAllGetMapping
    private PageDto<TrialStageDTO.ListItem> findByTrialId(
            @RequestParam(value = "trial_id") long trialId, Pageable pageable) {
        return Dto.from(trialStageService.findByTrialId(trialId, pageable), TrialStageDTO.ListItem.class);
    }

    @PostMapping("/admin/addNewTrialStage" )
    public ResponseEntity addNewTrialStage(@RequestBody AdminTrialStageDTO.ListItem adminTrialStageDTO) {

        try{
            adminTrialStageDTO.toDto(trialStageService.insert(adminTrialStageDTO));
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialStageDTO);
        return responseEntity;
    }

    @GetMapping("/admin/trialStageWithQuestions")
    private ResponseEntity trialStageWithQuestions(@RequestParam(value = "id") long id){


        AdminTrialStageDTO.FullItem adminTrialStageDTO = new AdminTrialStageDTO.FullItem();
        try{
            TrialStage trialStage = trialStageService.findById(id);
            adminTrialStageDTO.toDto(trialStage);
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialStageDTO);
        return responseEntity;
    }



    @DeleteMapping("/admin/deleteTrialStage")
    public ResponseEntity deleteTrial(@RequestParam(value = "id") long id) {
        try {
            trialStageService.delete(id);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("Trial Stage id =" + id + " is deleted");
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @PutMapping("/admin/updateTrialStage")
    public ResponseEntity updateTrial(@RequestBody AdminTrialStageDTO.ListItem adminTrialStageDTO) {

        try {
            adminTrialStageDTO.toDto(trialStageService.update(adminTrialStageDTO));
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialStageDTO);
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
