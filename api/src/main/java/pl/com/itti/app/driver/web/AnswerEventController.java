package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.core.annotation.FindAllGetMapping;
import pl.com.itti.app.driver.dto.AnswerEventDTO;
import pl.com.itti.app.driver.service.AnswerEventService;

import java.util.List;

@RestController
@RequestMapping("/api/answers-events")
public class AnswerEventController {

    @Autowired
    private AnswerEventService answerEventService;

    @FindAllGetMapping
    public List<AnswerEventDTO.Item> findAll(@RequestParam(value = "trialsession_id") long trialSessionId) {
        return answerEventService.getAnswersAndEvents(trialSessionId);
    }
}
