package pl.com.itti.app.driver.web;

import co.perpixel.dto.DTO;
import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.service.AnswerService;
import pl.com.itti.app.driver.util.SchemaValidationException;

import java.io.IOException;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public AnswerDTO.Item answerQuestions(@RequestBody AnswerDTO.Form form) throws IOException {
        Answer answer;
        try {
            answer = answerService.createAnswer(form);
        } catch (ValidationException ve) {
            if (ve.getCausingExceptions().isEmpty()) {
                throw new SchemaValidationException(ve);
            } else {
                throw new SchemaValidationException(ve.getCausingExceptions().get(0));
            }
        }
        return DTO.from(answer, AnswerDTO.Item.class);
    }
}
