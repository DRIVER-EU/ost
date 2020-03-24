package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.dto.AdminQuestionDTO;
import eu.fp7.driver.ost.driver.dto.AdminQuestionOptionDTO;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.Question;
import eu.fp7.driver.ost.driver.model.enums.AnswerType;
import eu.fp7.driver.ost.driver.repository.ObservationTypeRepository;
import eu.fp7.driver.ost.driver.repository.QuestionRepository;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Question question = questionRepository.findById(adminQuestionDTO.getId()).orElseThrow(() -> new InvalidDataException("No question found with given id = " + adminQuestionDTO.getId()));
        question.setName(adminQuestionDTO.getName());
        question.setDescription(adminQuestionDTO.getDescription());

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
        if(adminQuestionDTO.getJsonSchema() == null) {
            adminQuestionDTO.setJsonSchema(createJsonSchemaFromOptions(adminQuestionDTO));
        }

        Question question = Question.builder()
                .observationType(observationType)
                .name(adminQuestionDTO.getName())
                .answerType(adminQuestionDTO.getAnswerType())
                .description(adminQuestionDTO.getDescription())
                .commented(adminQuestionDTO.isCommented())
                .position(adminQuestionDTO.getPosition())
                .jsonSchema(adminQuestionDTO.getJsonSchema())
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
    public void updateJson(Long id)
    {
        Question question = questionRepository.findById(id).orElseThrow(() -> new InvalidDataException("No question found with given id = " + id));
        AdminQuestionDTO.FullItem fullQuestion = new AdminQuestionDTO.FullItem();
        fullQuestion.toDto(question);
        fullQuestion.jsonSchema =  createJsonSchemaFromOptions(fullQuestion);
        question.setJsonSchema(fullQuestion.getJsonSchema());
        questionRepository.save(question);
    }
    public String createJsonSchemaFromOptions(AdminQuestionDTO.FullItem adminQuestionDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            AnswerType answerType = adminQuestionDTO.getAnswerType();
            if (AnswerType.RADIO_BUTTON.equals(adminQuestionDTO.getAnswerType())) {
                objectNode.put("title", adminQuestionDTO.getName());
                objectNode.put("description", adminQuestionDTO.getDescription());
                objectNode.put("type", "string");
                String jsonStructureBeginingToString = objectNode.toString().replace("}", ",");
                ObjectNode objectEndingNode = objectMapper.createObjectNode();
                objectEndingNode.put("required", true);
                String response = jsonStructureBeginingToString + getEnumFromOptions(adminQuestionDTO, AnswerType.RADIO_BUTTON)
                        + buildJsonStuructureEnding(objectEndingNode);
                return response;
            } else if (AnswerType.TEXT_FIELD.equals(adminQuestionDTO.getAnswerType())) {
                objectNode.put("title", adminQuestionDTO.getName());
                objectNode.put("description", adminQuestionDTO.getDescription());
                objectNode.put("type", "string");
                String response = objectNode.toString();
                return response;
            } else if (AnswerType.CHECKBOX.equals(adminQuestionDTO.getAnswerType()))  {
                objectNode.put("type", "array");
                objectNode.put("title", adminQuestionDTO.getName());
                objectNode.put("description", adminQuestionDTO.getDescription());
                String jsonStructureBeginingToString = objectNode.toString().replace("}", ",");
                ObjectNode objectEndingNode = objectMapper.createObjectNode();
                objectEndingNode.put("uniqueItems", true);
                String response = jsonStructureBeginingToString + getEnumFromOptions(adminQuestionDTO, AnswerType.CHECKBOX)
                        + buildJsonStuructureEnding(objectEndingNode);
                return response;
            } else if (AnswerType.SLIDER.equals(adminQuestionDTO.getAnswerType())) {
                objectNode.put("title", adminQuestionDTO.getName());
                objectNode.put("description", adminQuestionDTO.getDescription());
                objectNode.put("type", "integer");
                objectNode.put("min", 1);
                objectNode.put("max", 10);
                objectNode.put("value", 2);
                objectNode.put("step", 1);
                objectNode.put("required", false);
                return objectNode.toString();
            } else if (AnswerType.RADIO_LINE.equals(adminQuestionDTO.getAnswerType())){
                objectNode.put("type", "string");
                objectNode.put("title", adminQuestionDTO.getName());
                objectNode.put("description", adminQuestionDTO.getDescription());
                String jsonStructureBeginingToString = objectNode.toString().replace("}", ",");
                ObjectNode objectEndingNode = objectMapper.createObjectNode();
                objectEndingNode.put("required", true);
                String response = jsonStructureBeginingToString + getEnumFromOptions(adminQuestionDTO, AnswerType.RADIO_LINE)
                        + buildJsonStuructureEnding(objectEndingNode);
                return response;
            } else if  (AnswerType.BOX_LIST.equals(adminQuestionDTO.getAnswerType())){
                throw new InvalidDataException("error by creating Json schema for the question, not supported type: " +answerType.toString());
            } else {
                throw new InvalidDataException("error by creating Json schema for the question, not supported type: " +answerType.toString());
            }

        } catch (Exception e) {
            throw new InvalidDataException("error by creating Json schema for the question ");
        }
    }

    private String createSimpleJson(AdminQuestionDTO.FullItem adminQuestionDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("title",adminQuestionDTO.getName());
        objectNode.put("description", adminQuestionDTO.getDescription() );
        objectNode.put("type", "string");
        String response = objectNode.toString();
        return response;
    }

    private String buildJsonStuructureEnding(ObjectNode objectNode) {
        String jsonStructureEndingToString = "";
        jsonStructureEndingToString = objectNode.toString().replace("{",",");

        return jsonStructureEndingToString;
    }

    private String getEnumFromOptions(AdminQuestionDTO.FullItem adminQuestionDTO, AnswerType answerType) {
        if (AnswerType.RADIO_LINE.equals(adminQuestionDTO.getAnswerType())) {
            return adminQuestionDTO.getQuestionOptions()
                    .stream()
                    .map(AdminQuestionOptionDTO.ListItem::getName)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining("\",\"", "\"enum\":[\"", "\"]"));
        }

    else if(AnswerType.CHECKBOX.equals(adminQuestionDTO.getAnswerType())){
            return adminQuestionDTO.getQuestionOptions()
                    .stream()
                    .map(AdminQuestionOptionDTO.ListItem::getName)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining("\",\"", "\"items\":{\"type\":\"string\",\"enum\":[\"", "\"]}"));
        } else if(AnswerType.RADIO_BUTTON.equals(adminQuestionDTO.getAnswerType())){
            return  adminQuestionDTO.getQuestionOptions()
                    .stream()
                    .map(AdminQuestionOptionDTO.ListItem::getName)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining("\",\"", "\"enum\":[\"", "\"]"));
        }
        throw new InvalidDataException("error by creating Json schema for the question, not supported type: " +answerType.toString());
    }
}
