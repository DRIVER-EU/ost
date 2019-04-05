package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.AttachmentType;
import pl.com.itti.app.driver.repository.*;
import pl.com.itti.app.driver.repository.specification.AnswerSpecification;
import pl.com.itti.app.driver.util.AnswerProperties;
import pl.com.itti.app.driver.util.CSVUtils;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static pl.com.itti.app.driver.util.SendToTestBed.sendToTestBed;
import static pl.com.itti.app.driver.util.SimulationTime.*;

@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerTrialRoleRepository answerTrialRoleRepository;

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

    @Autowired
    private AttachmentRepository attachmentRepository;

    public Answer createAnswer(AnswerDTO.Form form, MultipartFile[] files) throws ValidationException, IOException {
        ObservationType observationType = observationTypeRepository.findById(form.observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(ObservationType.class, form.observationTypeId));

        JSONObject jsonObject = SchemaCreator.getSchemaAsJSONObject(observationType.getQuestions());
        org.everit.json.schema.Schema schema = SchemaLoader.load(jsonObject);
        schema.validate(new JSONObject(form.formData.toString()));

        AuthUser currentUser = trialUserService.getCurrentUser();
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
                        .trialTime(Optional.ofNullable(getTrialTime()).orElse(LocalDateTime.now()))
                        .fieldValue(form.fieldValue)
                        .formData(form.formData.toString())
                        .comment(form.comment)
                        .build()
        );
        if (form.trialRoleIds != null) {
            answer.setAnswerTrialRoles(assignTrialRoles(form.trialRoleIds, answer));
        }
        answer.setAttachments(assignAttachments(form, files, answer));

        sendToTestBed(answer, observationType, trialSession);

        return answerRepository.save(answer);
    }

    public List<Answer> findAll(long trialSessionId, String text) {
        AuthUser currentUser = trialUserService.getCurrentUser();

        trialUserService.checkIsTrialSessionManager(currentUser, trialSessionId);

        List<Answer> answers = answerRepository.findAll(getAnswerSpecifications(trialSessionId));
        if (text != null && !text.equals("")) {
            List<Long> ids = findAnswerIdsByPattern(answers, text);
            answers = answers.stream()
                    .filter(answer -> ids.contains(answer.getId()))
                    .collect(Collectors.toList());
        }
        return answers;
    }

    private List<Long> findAnswerIdsByPattern(List<Answer> answers, String text) {
        List<Long> ids = new ArrayList<>();
        try (Analyzer analyzer = new StandardAnalyzer(); Directory directory = new RAMDirectory()) {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(directory, config);
            addDocumentsToWriter(answers, writer);
            writer.close();

            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            QueryParser parser = new QueryParser(AnswerProperties.TEXT, analyzer);
            Query query = parser.parse(text);
            ScoreDoc[] hits = searcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;
            for (ScoreDoc hit : hits) {
                Document hitDoc = searcher.doc(hit.doc);
                ids.add(Long.parseLong(hitDoc.get(AnswerProperties.ID)));
            }
            reader.close();
        } catch (IOException ioe) {
            throw new InternalServerException("IOError while searching", ioe);
        } catch (ParseException pe) {
            throw new InternalServerException("Parse error while searching", pe);
        }
        return ids;
    }

    public void removeAnswer(long answerId, String comment) {
        Optional<Answer> answer = answerRepository.findById(answerId);

        answer.ifPresent(ans -> {
            ans.setDeleteComment(comment);
            answerRepository.save(ans);
        });
    }

    private void addDocumentsToWriter(List<Answer> answers, IndexWriter writer) throws IOException {
        for (Answer answer : answers) {
            Document doc = new Document();
            doc.add(new Field(AnswerProperties.ID, String.valueOf(answer.getId()), TextField.TYPE_STORED));

            JsonNode node = new ObjectMapper().readTree(answer.getFormData());
            node.elements()
                    .forEachRemaining(jsonNode -> doc.add(new Field(AnswerProperties.TEXT, jsonNode.asText(), TextField.TYPE_STORED)));
            writer.addDocument(doc);
        }
    }

    private Specifications<Answer> getAnswerSpecifications(Long trialSessionId) {
        Set<Specification<Answer>> conditions = new HashSet<>();
        conditions.add(AnswerSpecification.inTrialSession(trialSessionId));
        conditions.add(AnswerSpecification.inLastTrialStage(trialSessionId));
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

    public Boolean hasAnswer(Long observationTypeId, AuthUser authUser) {
        Set<Specification<Answer>> conditions = new HashSet<>();
        conditions.add(AnswerSpecification.isAnswerForObservationType(observationTypeId));
        conditions.add(AnswerSpecification.isConnectedToAuthUser(authUser));
        List<Answer> observationTypes = answerRepository.findAll(RepositoryUtils.concatenate(conditions));
        return !observationTypes.isEmpty();
    }

    public void createCSVFile(FileWriter writer, long trialSessionId) throws IOException {
        List<String> title = Arrays.asList(
                AnswerProperties.TRIAL_ID,
                AnswerProperties.TRIAL_NAME,
                AnswerProperties.TRIAL_SESSION,
                AnswerProperties.TIME,
                AnswerProperties.USER,
                AnswerProperties.ROLE,
                AnswerProperties.OBSERVATION_TYPE_ID,
                AnswerProperties.OBSERVATION_TYPE,
                AnswerProperties.WHEN,
                AnswerProperties.TRIAL_TIME,
                AnswerProperties.QUESTION,
                AnswerProperties.ANSWER,
                AnswerProperties.COMMENT,
                AnswerProperties.LOCATION,
                AnswerProperties.ATTACHMENT_URI,
                AnswerProperties.ATTACHMENT_DESCRIPTION,
                AnswerProperties.DELETE_COMMENT,
                AnswerProperties.PRIMARY_COMMENT);

        CSVUtils.writeLine(writer, title);
        List<Answer> answers = answerRepository.findAllByTrialSessionId(trialSessionId);

        for (Answer answer : answers) {
            Iterator<Question> questionIterator = answer.getObservationType().getQuestions().iterator();
            do {
                Question question = new Question();
                if (questionIterator.hasNext()) {
                    question = questionIterator.next();
                }

                List<String> value = Arrays.asList(
                        String.valueOf(answer.getTrialSession().getTrial().getId()),
                        String.valueOf(answer.getTrialSession().getTrial().getName()),
                        String.valueOf(trialSessionId),
                        answer.getSentSimulationTime().format(DateTimeFormatter.ofPattern(AnswerProperties.TIME_PATTERN)),
                        answer.getTrialUser().getAuthUser().getFirstName() + " " + answer.getTrialUser().getAuthUser().getLastName(),
                        answer.getTrialUser().getUserRoleSessions().get(0).getTrialRole().getName(),
                        Long.toString(answer.getObservationType().getId()),
                        answer.getObservationType().getName(),
                        answer.getSimulationTime().format(DateTimeFormatter.ofPattern(AnswerProperties.TIME_PATTERN)),
                        answer.getTrialTime().format(DateTimeFormatter.ofPattern(AnswerProperties.TIME_PATTERN)),
                        SchemaCreator.getValueFromJSONObject(question.getJsonSchema(), AnswerProperties.TITLE_KEY),
                        SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId()),
                        SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY),
                        getLocation(answer.getAttachments()),
                        Optional.ofNullable(getUriOrDescription(answer.getAttachments(), AnswerProperties.ATTACHMENT_URI)).orElse(""),
                        Optional.ofNullable(getUriOrDescription(answer.getAttachments(), AnswerProperties.ATTACHMENT_DESCRIPTION)).orElse(""),
                        Optional.ofNullable(answer.getDeleteComment()).orElse(""),
                        answer.getComment());

                CSVUtils.writeLine(writer, value, ',', ' ');
            } while (questionIterator.hasNext());
        }
    }

    private String getLocation(List<Attachment> attachments) {
        Optional<Attachment> attachment = attachments.stream()
                .filter(attach -> attach.getType().name().contains(AttachmentType.LOCATION.name()))
                .findFirst();

        if (attachment.isPresent()) {
            return attachment.get().getLongitude() + ", " + attachment.get().getLatitude() + ", " + attachment.get().getAltitude();
        }

        return "";
    }

    private String getUriOrDescription(List<Attachment> attachments, String answerProperties) {
        Optional<Attachment> attachment = attachments.stream()
                .filter(attach -> attach.getType().name().contains(AttachmentType.DESCRIPTION.name()))
                .findFirst();

        if (!attachment.isPresent()) {
            return "";
        } else if(answerProperties.equals(AnswerProperties.ATTACHMENT_URI)) {
            return attachment.get().getUri();
        } else {
            return attachment.get().getDescription();
        }
    }
}
