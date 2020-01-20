package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.dto.AdminQuestionDTO;
import eu.fp7.driver.ost.driver.model.Question;
import eu.fp7.driver.ost.driver.service.QuestionService;
import eu.fp7.driver.ost.driver.service.TrialStageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private TrialStageService trialStageService;
    @Autowired
    private QuestionService questionService;

   @PostMapping("/admin/addNewQuestion" )
    public ResponseEntity addNewQuestion(@RequestBody AdminQuestionDTO.FullItem adminQuestionDTO) {

        try{
            adminQuestionDTO.toDto(questionService.insert(adminQuestionDTO));
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminQuestionDTO);
        return responseEntity;
    }

    @GetMapping("/admin/getFullQuestion")
    private ResponseEntity getFullQuestion(@RequestParam(value = "id") long id){


        AdminQuestionDTO.FullItem adminQuestionDTO = new AdminQuestionDTO.FullItem();
        try{
            Question question = questionService.findById(id);
            adminQuestionDTO.toDto(question);
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminQuestionDTO);
        return responseEntity;
    }



    @DeleteMapping("/admin/deleteQuestion")
    public ResponseEntity deleteQuestion(@RequestParam(value = "id") long id) {
        try {
            questionService.delete(id);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("Question id =" + id + " is deleted");
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @PutMapping("/admin/updateQuestion")
    public ResponseEntity updateQuestion(@RequestBody AdminQuestionDTO.FullItem adminQuestionDTO) {

        try {
            adminQuestionDTO.toDto(questionService.update(adminQuestionDTO));
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminQuestionDTO);
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
