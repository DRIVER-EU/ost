package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
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
import org.everit.json.schema.Schema;
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
        Schema schema = SchemaLoader.load(jsonObject);
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
                        .fieldValue(form.fieldValue)
                        .formData(form.formData.toString())
                        .build()
        );
        if (form.trialRoleIds != null) {
            answer.setAnswerTrialRoles(assignTrialRoles(form.trialRoleIds, answer));
        }
        answer.setAttachments(assignAttachments(form, files, answer));

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

    public void removeAnswer(long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);

        if (answer.isPresent()) {
            attachmentRepository.delete(answer.get().getAttachments());
            answerTrialRoleRepository.delete(answer.get().getAnswerTrialRoles());
            answerRepository.delete(answer.get());
        }
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
        List<String> title = Arrays.asList(AnswerProperties.ANSWER_ID,
                AnswerProperties.TIME,
                AnswerProperties.USER,
                AnswerProperties.ROLE,
                AnswerProperties.OBSERVATION_TYPE_ID,
                AnswerProperties.OBSERVATION_TYPE,
                AnswerProperties.WHEN,
                AnswerProperties.QUESTION,
                AnswerProperties.ANSWER,
                AnswerProperties.COMMENT,
                AnswerProperties.LOCATION,
                AnswerProperties.ATTACHMENT);

        CSVUtils.writeLine(writer, title);
        List<Answer> answers = answerRepository.findAll(getAnswerSpecifications(trialSessionId));

        for (Answer answer : answers) {
            for (Question question : answer.getObservationType().getQuestions()) {
                List<String> value = Arrays.asList(Long.toString(answer.getId()),
                    answer.getSentSimulationTime().format(DateTimeFormatter.ofPattern(AnswerProperties.TIME_PATTERN)),
                    answer.getTrialUser().getAuthUser().getFirstName() + " " + answer.getTrialUser().getAuthUser().getLastName(),
                    trialRoleRepository.findById(answer.getTrialUser().getId()).get().getName(),
                    Long.toString(answer.getObservationType().getId()),
                    answer.getObservationType().getName(),
                    answer.getSimulationTime().format(DateTimeFormatter.ofPattern(AnswerProperties.TIME_PATTERN)),
                    SchemaCreator.getValueFromJSONObject(question.getJsonSchema(), AnswerProperties.TITLE_KEY),
                    SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId()),
                    SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY),
                    getLocation(answer.getAttachments()),
                    getUriOrDescription(answer.getAttachments()));

                CSVUtils.writeLine(writer, value, ',', '"');
            }
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

    private String getUriOrDescription(List<Attachment> attachments) {
        Optional<Attachment> attachment = attachments.stream()
                .filter(attach -> attach.getType().name().contains(AttachmentType.DESCRIPTION.name()))
                .findFirst();

        if (attachment.isPresent() && !Strings.isNullOrEmpty(attachment.get().getUri())) {
            return attachment.get().getUri();
        } else if (attachment.isPresent()) {
            return attachment.get().getDescription();
        } else {
            return "";
        }
    }
}
