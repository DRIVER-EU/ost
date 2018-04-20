package pl.com.itti.app.driver.service;

import co.perpixel.dto.DTO;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.AnswerEventDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.repository.AnswerRepository;
import pl.com.itti.app.driver.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnswerEventService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Transactional(readOnly = true)
    public List<AnswerEventDTO.Item> getAnswersAndEvents(long trialSessionId) {
        AuthUser currentUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(RuntimeException::new);

        trialUserService.checkHasTrialSession(currentUser, trialSessionId);

        List<Answer> answers = answerRepository.findAllByTrialSessionId(trialSessionId);
        List<Event> events = eventRepository.findAllByTrialSessionId(trialSessionId);

        return getAsAnswersEvents(answers, events);
    }

    private List<AnswerEventDTO.Item> getAsAnswersEvents(List<Answer> answers, List<Event> events) {
        List<AnswerEventDTO.Item> answerEventList = new ArrayList<>();
        answerEventList.addAll(mapAnswers(answers));
        answerEventList.addAll(mapEvents(events));
        return answerEventList;
    }

    private List<AnswerEventDTO.AnswerItem> mapAnswers(List<Answer> answers) {
        return DTO.from(answers, AnswerEventDTO.AnswerItem.class);
    }

    private List<AnswerEventDTO.EventItem> mapEvents(List<Event> events) {
        return DTO.from(events, AnswerEventDTO.EventItem.class);
    }
}
