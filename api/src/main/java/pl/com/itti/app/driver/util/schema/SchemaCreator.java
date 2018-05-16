package pl.com.itti.app.driver.util.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import pl.com.itti.app.driver.model.Attachment;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.model.enums.AnswerType;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public final class SchemaCreator {

    private static final String QUESTION_ID = "question_";

    private static final String FIELD_DISABLED = "ui:disabled";

    private static final String FIELD_WIDGET = "ui:widget";

    private static final String FIELD_DESCRIPTION = "descriptions";

    private static final String FIELD_COORDINATES = "coordinates";

    private static final String FIELD_FILE = "files";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ObjectNode COMMENT_SCHEMA = MAPPER.createObjectNode()
            .put("title", "Comment")
            .put("description", "What you think about this question?")
            .put("type", "string");

    private static final ObjectNode COMMENT_UI_SCHEMA = MAPPER.createObjectNode()
            .put(FIELD_DISABLED, false);

    public static ObjectNode createSchemaForm(List<Question> questions, boolean disabled) throws IOException {
        questions.sort(Comparator.comparing(Question::getPosition));
        ObjectNode schemaForm = MAPPER.createObjectNode();

        ObjectNode schema = createSchema(questions);
        schemaForm.putPOJO("schema", schema);

        ObjectNode uiSchema = createUiSchema(questions, disabled);
        schemaForm.putPOJO("uiSchema", uiSchema);

        return schemaForm;
    }

    public static JSONObject getSchemaAsJSONObject(List<Question> questions) throws IOException {
        return new JSONObject(createSchema(questions).toString());
    }

    private static ObjectNode createSchema(List<Question> questions) throws IOException {
        ObjectNode schema = MAPPER.createObjectNode();
        schema.put("type", "object");

        ObjectNode properties = MAPPER.createObjectNode();
        for (Question question : questions) {
            properties.putPOJO(QUESTION_ID + question.getId(), createPropertyContent(question));
            if (question.isCommented()) {
                properties.putPOJO(QUESTION_ID + question.getId() + "_comment", COMMENT_SCHEMA);
            }
        }
        schema.putPOJO("properties", properties);

        return schema;
    }

    private static JsonNode createPropertyContent(Question question) throws IOException {
        return MAPPER.readTree(question.getJsonSchema());
    }

    private static ObjectNode createUiSchema(List<Question> questions, boolean disabled) {
        ObjectNode schema = MAPPER.createObjectNode();

        questions.forEach(question -> {
            schema.putPOJO(QUESTION_ID + question.getId(), createUiSchema(question, disabled));
            if (question.isCommented()) {
                schema.putPOJO(QUESTION_ID + question.getId() + "_comment", COMMENT_UI_SCHEMA);
            }
        });
        return schema;
    }

    private static ObjectNode createUiSchema(Question question, boolean disabled) {
        ObjectNode ui = MAPPER.createObjectNode();
        ui.put(FIELD_DISABLED, disabled);

        if (question.getAnswerType().equals(AnswerType.RADIO_BUTTON)) {
            ui.put(FIELD_WIDGET, "radio");
        } else if (question.getAnswerType().equals(AnswerType.SLIDER)) {
            ui.put(FIELD_WIDGET, "slider");
        }

        return ui;
    }

    public static ObjectNode createAttachmentSchemaForm(List<Attachment> attachments) {
        ArrayNode descriptionsNode = MAPPER.createArrayNode();
        ArrayNode filesNode = MAPPER.createArrayNode();
        ArrayNode coordinatesNode = MAPPER.createArrayNode();

        for (Attachment attachment : attachments) {
            switch (attachment.getType()) {
                case DESCRIPTION:
                    descriptionsNode.addPOJO(new DescriptionNode(attachment));
                    break;
                case LOCATION:
                    coordinatesNode.addPOJO(new LocationNode(attachment));
                    break;
                case PICTURE:
                    filesNode.addPOJO(new FileNode(attachment));
                    break;
                default:
                    break;
            }
        }

        return MAPPER.createObjectNode()
                .putPOJO(FIELD_DESCRIPTION, descriptionsNode)
                .putPOJO(FIELD_COORDINATES, coordinatesNode)
                .putPOJO(FIELD_FILE, filesNode);
    }
}
