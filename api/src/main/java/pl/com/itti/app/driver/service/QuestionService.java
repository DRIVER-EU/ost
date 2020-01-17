package pl.com.itti.app.driver.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.AdminQuestionDTO;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.QuestionRepository;
import pl.com.itti.app.driver.util.InvalidDataException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;


    @Transactional(readOnly = true)
    public Question findById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new InvalidDataException("No question found with given id = " + id));
        return question;
    }

    @Transactional
    public void delete(long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new InvalidDataException("No question found with given id = " + id));
        questionRepository.delete(question);
    }

    @Transactional
    public Question update(AdminQuestionDTO.FullItem adminQuestionDTO) {

        if (adminQuestionDTO.getId() == 0) {
            throw new InvalidDataException("No question Id was given");
        }
        if(adminQuestionDTO.jsonSchema == null || adminQuestionDTO.jsonSchema.isEmpty())
        {
            adminQuestionDTO.jsonSchema =  createJsonSchema(adminQuestionDTO);
        }

        Question question = questionRepository.findById(adminQuestionDTO.getId()).orElseThrow(() -> new InvalidDataException("No question found with given id = " + adminQuestionDTO.getId()));
        question.setName(adminQuestionDTO.getName());
        question.setDescription(adminQuestionDTO.getDescription());
        question.setJsonSchema(adminQuestionDTO.getJsonSchema());
        question.setPosition(adminQuestionDTO.getPosition());
        question.setCommented(adminQuestionDTO.isCommented());
        question.setAnswerType(adminQuestionDTO.getAnswerType());

        return questionRepository.save(question);
    }

    @Transactional
    public Question insert(AdminQuestionDTO.FullItem adminQuestionDTO) {
        if (adminQuestionDTO.getObservationTypeId() == 0) {
            new InvalidDataException("Data Error missing observation type in request ");
        }
        ObservationType observationType = observationTypeRepository.findById(adminQuestionDTO.getObservationTypeId())
                .orElseThrow(() -> new InvalidDataException("No observation type found with given id = " + adminQuestionDTO.getObservationTypeId()));

        if(adminQuestionDTO.jsonSchema == null || adminQuestionDTO.jsonSchema.isEmpty())
        {
            adminQuestionDTO.jsonSchema =  createJsonSchema(adminQuestionDTO);
        }

        Question question = Question.builder()
                .observationType(observationType)
                .name(adminQuestionDTO.getName())
                .answerType(adminQuestionDTO.getAnswerType())
                .description(adminQuestionDTO.getDescription())
                .commented(adminQuestionDTO.isCommented())
                .position(adminQuestionDTO.getPosition())
                .jsonSchema(adminQuestionDTO.jsonSchema)
                .build();

        return questionRepository.save(question);

    }

    @Transactional
    public AdminQuestionDTO.FullItem getFullQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new InvalidDataException("No question found with given id = " + id));
        AdminQuestionDTO.FullItem fullQuestion = new AdminQuestionDTO.FullItem();
        fullQuestion.toDto(question);
        return fullQuestion;
    }

    private String createJsonSchema(AdminQuestionDTO.FullItem adminQuestionDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("title", adminQuestionDTO.getName());
            objectNode.put("description", adminQuestionDTO.getDescription());
            String answerType = adminQuestionDTO.getAnswerType().toString();
            String enumString = "";
            String jsonStructureToString = "";
            if (answerType.equals("RADIO_BUTTON")) {
                objectNode.put("type", "string");
                enumString = Arrays.asList(adminQuestionDTO.getDescription())
                        .stream()
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.joining("\",\"", "\"enum\":[\"", "\"]}"));
                jsonStructureToString = objectNode.toString().replace("}", ",");
            } else if (answerType.equals("TEXT_FIELD")) {
                objectNode.put("type", "string");
                jsonStructureToString = objectNode.toString();
            } else if (answerType.equals("CHECKBOX")) {
                objectNode.put("type", "boolean");
                jsonStructureToString = objectNode.toString();
            } else if (answerType.equals("SLIDER")) {
                objectNode.put("type", "number");
                jsonStructureToString = objectNode.toString();
            } else {
                objectNode.put("type", adminQuestionDTO.getAnswerType().toString());
                jsonStructureToString = objectNode.toString();
            }
            return jsonStructureToString + enumString;
        } catch (Exception e) {
            throw new InvalidDataException("error by creating Json schema for the question ");
        }
    }
}
