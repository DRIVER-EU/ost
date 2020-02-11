package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.dto.AdminQuestionOptionDTO;
import eu.fp7.driver.ost.driver.model.QuestionOption;
import eu.fp7.driver.ost.driver.service.QuestionOptionService;
import eu.fp7.driver.ost.driver.service.QuestionService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/questionOptions")
public class QuestionOptionController {

    @Autowired
    private QuestionOptionService questionOptionService;
    @Autowired
    private QuestionService questionService;

   @PostMapping("/admin/addNewQuestionOption" )
    public ResponseEntity addNewQuestion(@RequestBody AdminQuestionOptionDTO.FullItem adminQuestionOptionDTO) {

        try{
            adminQuestionOptionDTO.toDto(questionOptionService.insert(adminQuestionOptionDTO));
            questionService.updateJonson(adminQuestionOptionDTO.questionId);
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminQuestionOptionDTO);
        return responseEntity;
    }

    @GetMapping("/admin/getFullQuestionOption")
    private ResponseEntity getFullQuestion(@RequestParam(value = "id") long id){


        AdminQuestionOptionDTO.FullItem adminQuestionOptionDTO = new AdminQuestionOptionDTO.FullItem();
        try{
            QuestionOption questionOption = questionOptionService.findById(id);
            adminQuestionOptionDTO.toDto(questionOption);
        }
        catch (Exception e)
        {
            return  getResponseEntity(e);
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminQuestionOptionDTO);
        return responseEntity;
    }



    @DeleteMapping("/admin/deleteQuestionOption")
    public ResponseEntity deleteQuestionOption(@RequestParam(value = "id") long id) {
        try {
            QuestionOption questionOption = questionOptionService.findById(id);
            questionOptionService.delete(id);
            questionService.updateJonson(questionOption.getQuestion().getId());
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("Question Option id =" + id + " is deleted");

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
