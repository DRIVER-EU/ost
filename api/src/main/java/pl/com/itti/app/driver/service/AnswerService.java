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
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.repository.*;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerTrialRoleRepository answerTrialRoleRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

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

    public Answer createAnswer(AnswerDTO.Form form, MultipartFile[] files) throws ValidationException, IOException {
        ObservationType observationType = observationTypeRepository.findById(form.observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, form.observationTypeId));

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

        assignTrialRoles(form.trialRoleIds, answer);
        answer.setAttachments(assignAttachments(form, files, answer));

        return answerRepository.save(answer);
    }

    private List<Attachment> assignAttachments(AnswerDTO.Form form, MultipartFile[] files, Answer answer) throws IOException {
        List<Attachment> attachments = new ArrayList<>();

        attachments.addAll(attachmentService.createDescriptionAttachments(form.descriptions, answer));
//        attachments.addAll(attachmentService.createLocationAttachments(form.coordinates, answer));
        attachments.addAll(attachmentService.createFileAttachments(files, answer));

        return attachmentRepository.save(attachments);
    }

    private List<AnswerTrialRole> assignTrialRoles(List<Long> trialRoleIds, Answer answer) {
        List<AnswerTrialRole> answerTrialRoles = new ArrayList<>();

        trialRoleIds.forEach(trialRoleId -> {
            AnswerTrialRoleId answerTrialRoleId = new AnswerTrialRoleId();
            answerTrialRoleId.setAnswerId(answer.getId());
            answerTrialRoleId.setTrialRoleId(trialRoleId);

            TrialRole trialRole = trialRoleRepository.findById(trialRoleId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialRole.class, trialRoleId));

            answerTrialRoles.add(new AnswerTrialRole(answerTrialRoleId, answer, trialRole));
        });

        return answerTrialRoleRepository.save(answerTrialRoles);
    }
}
