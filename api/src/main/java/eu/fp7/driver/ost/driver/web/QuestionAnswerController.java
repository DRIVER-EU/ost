package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.driver.dto.QuestionAnswerDTO;
import eu.fp7.driver.ost.driver.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
