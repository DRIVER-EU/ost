package pl.com.itti.app.driver.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.AdminQuestionDTO;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.service.QuestionService;
import pl.com.itti.app.driver.service.TrialStageService;

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
