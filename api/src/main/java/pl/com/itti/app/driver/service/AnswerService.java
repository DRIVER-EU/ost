package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.repository.*;
import pl.com.itti.app.driver.repository.specification.AnswerSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerTrialRoleRepository answerTrialRoleRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private TrialUserService trialUserService;

    public Answer createAnswer(AnswerDTO.Form form, MultipartFile[] files) throws ValidationException, IOException {
        ObservationType observationType = observationTypeRepository.findById(form.observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(ObservationType.class, form.observationTypeId));

        JSONObject jsonObject = SchemaCreator.getSchemaAsJSONObject(observationType.getQuestions());
        Schema schema = SchemaLoader.load(jsonObject);
        schema.validate(new JSONObject(form.formData.toString()));

        AuthUser currentUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
        TrialUser currentTrialUser = trialUserRepository.findByAuthUser(currentUser);
        TrialSession trialSession = trialSessionRepository.findById(form.trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, form.trialSessionId));

        Answer answer = answerRepository.save(
                Answer.builder()
                        .trialSession(trialSession)
                        .trialUser(currentTrialUser)
                        .observationType(observationType)
                        .simulationTime(form.simulationTime)
                        .sentSimulationTime(LocalDateTime.now())
                        .fieldValue(form.fieldValue)
                        .formData(form.formData.toString())
                        .build()
        );

        answer.setAnswerTrialRoles(assignTrialRoles(form.trialRoleIds, answer));
        answer.setAttachments(assignAttachments(form, files, answer));

        return answerRepository.save(answer);
    }

    public Page<Answer> findAll(long trialSessionId, Pageable pageable) {
        AuthUser currentUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        trialUserService.checkIsTrialSessionManager(currentUser, trialSessionId);

        return answerRepository.findAll(getAnswerSpecifications(trialSessionId), pageable);
    }

    private Specifications<Answer> getAnswerSpecifications(Long trialSessionId) {
        Set<Specification<Answer>> conditions = new HashSet<>();
        conditions.add(AnswerSpecification.inTrialSession(trialSessionId));
        return RepositoryUtils.concatenate(conditions);
    }

    private List<Attachment> assignAttachments(AnswerDTO.Form form, MultipartFile[] files, Answer answer) throws IOException {
        return attachmentService.createAttachments(form.descriptions, form.coordinates, files, answer);
    }

    private List<AnswerTrialRole> assignTrialRoles(List<Long> trialRoleIds, Answer answer) {
        List<AnswerTrialRole> answerTrialRoles = new ArrayList<>();

        for (Long trialRoleId : trialRoleIds) {
            AnswerTrialRoleId answerTrialRoleId = new AnswerTrialRoleId();
            answerTrialRoleId.setAnswerId(answer.getId());
            answerTrialRoleId.setTrialRoleId(trialRoleId);

            TrialRole trialRole = trialRoleRepository.findById(trialRoleId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialRole.class, trialRoleId));

            answerTrialRoles.add(new AnswerTrialRole(answerTrialRoleId, answer, trialRole));
        }

        return answerTrialRoleRepository.save(answerTrialRoles);
    }


}
