package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
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
import pl.com.itti.app.driver.repository.*;
import pl.com.itti.app.driver.repository.specification.AnswerSpecification;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerService {

    private static final String ID = "id";

    private static final String TEXT = "text";

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

        answer.setAnswerTrialRoles(assignTrialRoles(form.trialRoleIds, answer));
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

            QueryParser parser = new QueryParser(TEXT, analyzer);
            Query query = parser.parse(text);
            ScoreDoc[] hits = searcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;
            for (ScoreDoc hit : hits) {
                Document hitDoc = searcher.doc(hit.doc);
                ids.add(Long.parseLong(hitDoc.get(ID)));
            }
            reader.close();
        } catch (IOException ioe) {
            throw new InternalServerException("IOError while searching", ioe);
        } catch (ParseException pe) {
            throw new InternalServerException("Parse error while searching", pe);
        }
        return ids;
    }

    private void addDocumentsToWriter(List<Answer> answers, IndexWriter writer) throws IOException {
        for (Answer answer : answers) {
            Document doc = new Document();
            doc.add(new Field(ID, String.valueOf(answer.getId()), TextField.TYPE_STORED));

            JsonNode node = new ObjectMapper().readTree(answer.getFormData());
            node.elements()
                    .forEachRemaining(jsonNode -> doc.add(new Field(TEXT, jsonNode.asText(), TextField.TYPE_STORED)));
            writer.addDocument(doc);
        }
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
