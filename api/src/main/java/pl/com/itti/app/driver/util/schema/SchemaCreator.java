package pl.com.itti.app.driver.util.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.model.enums.AnswerType;

import java.io.IOException;
import java.util.List;

public final class SchemaCreator {

    private static final String QUESTION_ID = "question_";

    private static final String DISABLED = "ui:disabled";

    private static final String WIDGET = "ui:widget";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static ObjectNode createSchemaForm(List<Question> questions) throws IOException {
        ObjectNode schemaForm = MAPPER.createObjectNode();

        ObjectNode schema = createSchema(questions);
        schemaForm.putPOJO("schema", schema);

        ObjectNode uiSchema = createUiSchema(questions);
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
        }
        schema.putPOJO("properties", properties);

        return schema;
    }

    private static JsonNode createPropertyContent(Question question) throws IOException {
        return MAPPER.readTree(question.getJsonSchema());
    }

    private static ObjectNode createUiSchema(List<Question> questions) {
        ObjectNode schema = MAPPER.createObjectNode();
        questions.forEach(question -> schema.putPOJO(QUESTION_ID + question.getId(), createUiSchema(question)));
        return schema;
    }

    private static ObjectNode createUiSchema(Question question) {
        ObjectNode ui = MAPPER.createObjectNode();
        ui.put(DISABLED, false);

        if (question.getAnswerType().equals(AnswerType.RADIO_BUTTON)) {
            ui.put(WIDGET, "radio");
        } else if (question.getAnswerType().equals(AnswerType.SLIDER)) {
            ui.put(WIDGET, "slider");
        }

        return ui;
    }
}
