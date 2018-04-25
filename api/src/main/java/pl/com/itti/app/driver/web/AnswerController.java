package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindOneGetMapping;
import co.perpixel.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @FindOneGetMapping
    public AnswerDTO.SchemaItem findById(@PathVariable(value = "id") long id) {
        return DTO.from(answerService.getAnswerById(id), AnswerDTO.SchemaItem.class);
    }
}
