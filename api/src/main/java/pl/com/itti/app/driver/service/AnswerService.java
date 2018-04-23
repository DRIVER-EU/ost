package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.repository.AnswerRepository;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialUserRepository trialUserRepository;

    public Answer createAnswer(AnswerDTO.Form form) throws ValidationException, IOException {
        ObservationType observationType = observationTypeRepository.findById(form.observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, form.observationTypeId));

        JSONObject jsonObject = SchemaCreator.getSchemaAsJSONObject(observationType.getQuestions());
        Schema schema = SchemaLoader.load(jsonObject);
        schema.validate(new JSONObject(form.data.toString()));

        AuthUser currentUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
        TrialUser currentTrialUser = trialUserRepository.findByAuthUser(currentUser);

        TrialSession trialSession = trialSessionRepository.findById(form.trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, form.trialSessionId));

        Answer answer = Answer.builder()
                .trialSession(trialSession)
                .trialUser(currentTrialUser)
                .observationType(observationType)
                .simulationTime(LocalDateTime.now())
                .sentSimulationTime(form.sentSimulationTime.toLocalDateTime())
                .fieldValue("")
                .formData(form.data.toString())
                .attachments(new ArrayList<>())
                .answerTrialRoles(new ArrayList<>())
                .build();

        return answerRepository.save(answer);
    }
}
