package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.core.annotation.FindAllGetMapping;
import eu.fp7.driver.ost.driver.dto.AnswerEventDTO;
import eu.fp7.driver.ost.driver.service.AnswerEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
