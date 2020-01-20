package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.model.AnswerTrialRole;
import eu.fp7.driver.ost.driver.model.Attachment;
import eu.fp7.driver.ost.driver.model.Event;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialSessionManager;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.repository.AnswerRepository;
import eu.fp7.driver.ost.driver.repository.AnswerTrialRoleRepository;
import eu.fp7.driver.ost.driver.repository.AttachmentRepository;
import eu.fp7.driver.ost.driver.repository.EventRepository;
import eu.fp7.driver.ost.driver.repository.TrialRepository;
import eu.fp7.driver.ost.driver.repository.TrialSessionManagerRepository;
import eu.fp7.driver.ost.driver.repository.TrialSessionRepository;
import eu.fp7.driver.ost.driver.repository.TrialStageRepository;
import eu.fp7.driver.ost.driver.repository.UserRoleSessionRepository;
import eu.fp7.driver.ost.driver.util.TrailDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OstSessionService {

  @Autowired
  TrialRepository trialRepository;

  @Autowired
  TrialSessionRepository trialSessionRepository;

  @Autowired
  TrialStageRepository trialStageRepository;

  @Autowired
  AnswerRepository answerRepository;

  @Autowired
  AttachmentRepository attachmentRepository;

  @Autowired
  AnswerTrialRoleRepository answerTrialRoleRepository;

  @Autowired
  UserRoleSessionRepository userRoleSessionRepository;

  @Autowired
  EventRepository eventRepository;

  @Autowired
  TrialSessionManagerRepository trialSessionManagerRepository;

  @Transactional
  public void deleteTrialSession(Long ostSessionId) throws TrailDeleteException {

    Optional<TrialSession> trialSession = trialSessionRepository.findById(ostSessionId);

    if (!trialSession.isPresent()) {
      throw new TrailDeleteException("Trial session for given id =" + ostSessionId + " was not found.");
    }
    String deletingActionMessage = "";
    try {

      for (Event events : trialSession.get().getEvents()) {
        deletingActionMessage = "Deleting events id= " + events.id;
        eventRepository.delete(events);
      }

      for (TrialSessionManager trialSessionManagers : trialSession.get().getTrialSessionManagers()) {
        deletingActionMessage = "Deleting trialSessionManager id= " + trialSessionManagers.getId();
        trialSessionManagerRepository.delete(trialSessionManagers);
      }

      List<Answer> answerList = answerRepository.findAllByTrialSessionId(ostSessionId);
      for (Answer answer : answerList) {
        for (Attachment attachment : attachmentRepository.findByAnswer(answer)) {
          deletingActionMessage = "Deleting attachment id= " + attachment.id;
          attachmentRepository.delete(attachment);
        }

        for (AnswerTrialRole answerTrialRole : answerTrialRoleRepository.findByAnswer(answer)) {
          deletingActionMessage = "Deleting answer TrialRole = " + answerTrialRole.getTrialRole();
          answerTrialRoleRepository.delete(answerTrialRole);
        }
        answerRepository.delete(answer.getId());
      }
      for (UserRoleSession userRoleSession : userRoleSessionRepository.findByTrialSession(trialSession.get())) {
        deletingActionMessage = "Deleting userRoleSession = " + userRoleSession.getTrialRole();
        userRoleSessionRepository.delete(userRoleSession);
      }
      deletingActionMessage = "Deleting userRoleSession = " + trialSession.get().getId();
      trialSessionRepository.delete(trialSession.get());
    } catch (Exception e) {
      throw new TrailDeleteException("Can't delete Trial session for given id =" + ostSessionId + ". ERROR occured by = " + deletingActionMessage, e);
    }

  }
}
