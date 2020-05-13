package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.dto.AnswerEventDTO;
import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.model.Event;
import eu.fp7.driver.ost.driver.repository.AnswerRepository;
import eu.fp7.driver.ost.driver.repository.EventRepository;
import eu.fp7.driver.ost.driver.repository.specification.AnswerSpecification;
import eu.fp7.driver.ost.driver.repository.specification.EventSpecification;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String keycloakUserId = trialUserService.getCurrentKeycloakUserId();
        List<Answer> answers = answerRepository.findAll(getAnswerSpecifications(keycloakUserId, trialSessionId));
        List<Event> events = eventRepository.findAll(getEventSpecifications(keycloakUserId, trialSessionId));

        List<AnswerEventDTO.Item> items = getAsAnswersEvents(answers, events);
        items.sort(Comparator.comparing(item -> item.time));
        Collections.reverse(items);
        return items;
    }

    private Specifications<Answer> getAnswerSpecifications(String keycloakUserId, Long trialSessionId) {
        Set<Specification<Answer>> conditions = new HashSet<>();
        conditions.add(AnswerSpecification.isConnectedToAuthUser(keycloakUserId));
        conditions.add(AnswerSpecification.inTrialSession(trialSessionId));
        conditions.add(AnswerSpecification.inLastTrialStage(trialSessionId));
        conditions.add(AnswerSpecification.isNotDeleted());
        return RepositoryUtils.concatenate(conditions);
    }

    private Specifications<Event> getEventSpecifications(String keycloakUserId, Long trialSessionId) {
        Set<Specification<Event>> conditions = new HashSet<>();
        conditions.add(EventSpecification.isConnectedToAuthUser(keycloakUserId));
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
        return Dto.from(answers, AnswerEventDTO.AnswerItem.class);
    }

    private List<AnswerEventDTO.EventItem> mapEvents(List<Event> events) {
        return Dto.from(events, AnswerEventDTO.EventItem.class);
    }
}
