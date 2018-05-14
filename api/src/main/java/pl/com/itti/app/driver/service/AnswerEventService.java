package pl.com.itti.app.driver.service;

import co.perpixel.dto.DTO;
import co.perpixel.security.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.AnswerEventDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.repository.AnswerRepository;
import pl.com.itti.app.driver.repository.EventRepository;
import pl.com.itti.app.driver.repository.specification.AnswerSpecification;
import pl.com.itti.app.driver.repository.specification.EventSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.*;

@Service
@Transactional
public class AnswerEventService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Transactional(readOnly = true)
    public List<AnswerEventDTO.Item> getAnswersAndEvents(long trialSessionId) {
        AuthUser currentUser = trialUserService.getCurrentUser();
        List<Answer> answers = answerRepository.findAll(getAnswerSpecifications(currentUser, trialSessionId));
        List<Event> events = eventRepository.findAll(getEventSpecifications(currentUser, trialSessionId));

        List<AnswerEventDTO.Item> items = getAsAnswersEvents(answers, events);
        items.sort(Comparator.comparing(item -> item.time));
        return items;
    }

    private Specifications<Answer> getAnswerSpecifications(AuthUser authUser, Long trialSessionId) {
        Set<Specification<Answer>> conditions = new HashSet<>();
        conditions.add(AnswerSpecification.isConnectedToAuthUser(authUser));
        conditions.add(AnswerSpecification.inTrialSession(trialSessionId));
        conditions.add(AnswerSpecification.inLastTrialStage(trialSessionId));
        return RepositoryUtils.concatenate(conditions);
    }

    private Specifications<Event> getEventSpecifications(AuthUser authUser, Long trialSessionId) {
        Set<Specification<Event>> conditions = new HashSet<>();
        conditions.add(EventSpecification.isConnectedToAuthUser(authUser));
        conditions.add(EventSpecification.inTrialSession(trialSessionId));
        return RepositoryUtils.concatenate(conditions);
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
