package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.QuestionAnswerDTO;
import pl.com.itti.app.driver.service.QuestionAnswerService;
import pl.com.itti.app.core.dto.Dto;

@RestController
@RequestMapping("/api/questions-answers")
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @GetMapping
    public QuestionAnswerDTO.FullItem findByAnswerId(@RequestParam(value = "answer_id") long answerId) {
        return Dto.from(questionAnswerService.getByAnswerId(answerId), QuestionAnswerDTO.FullItem.class);
    }

    @PostMapping("/{answer_id:\\d+}/comment")
    public void addComment(@PathVariable(value = "answer_id") long answerId,
                                @RequestParam("comment") String comment) {
        questionAnswerService.addComment(answerId, comment);
    }
}
