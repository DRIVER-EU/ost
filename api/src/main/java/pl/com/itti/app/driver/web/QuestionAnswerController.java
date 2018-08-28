package pl.com.itti.app.driver.web;

import co.perpixel.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.QuestionAnswerDTO;
import pl.com.itti.app.driver.service.QuestionAnswerService;

@RestController
@RequestMapping("/api/questions-answers")
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @GetMapping
    public QuestionAnswerDTO.FullItem findByAnswerId(@RequestParam(value = "answer_id") long answerId) {
        return DTO.from(questionAnswerService.getByAnswerId(answerId), QuestionAnswerDTO.FullItem.class);
    }

    @PostMapping("/{answer_id:\\d+}/putComment")
    public void createNewSesson(@PathVariable(value = "answer_id") long answerId,
                                @RequestParam("comment") String comment) {
        questionAnswerService.putComment(answerId, comment);
    }
}
