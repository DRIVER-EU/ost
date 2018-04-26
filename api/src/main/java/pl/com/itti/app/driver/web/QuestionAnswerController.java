package pl.com.itti.app.driver.web;

import co.perpixel.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.QuestionAnswerDTO;
import pl.com.itti.app.driver.service.QuestionAnswerService;

@RestController
@RequestMapping("/api/questions-answers")
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @GetMapping
    public QuestionAnswerDTO.SchemaItem findByAnswerId(@RequestParam(value = "answer_id") long answerId) {
        return DTO.from(questionAnswerService.getByAnswerId(answerId), QuestionAnswerDTO.SchemaItem.class);
    }
}
