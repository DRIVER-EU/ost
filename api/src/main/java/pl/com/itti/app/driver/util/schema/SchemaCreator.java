package pl.com.itti.app.driver.util.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static JSONObject createSchemaForm(List<Question> questions) throws IOException {
        JSONObject schemaForm = MAPPER.createJSONObject();

        JSONObject schema = createSchema(questions);
        schemaForm.putPOJO("schema", schema);

        JSONObject uiSchema = createUiSchema(questions);
        schemaForm.putPOJO("uiSchema", uiSchema);

        return schemaForm;
    }

    private static JSONObject createSchema(List<Question> questions) throws IOException {
        JSONObject schema = MAPPER.createJSONObject();
        schema.put("type", "object");

        JSONObject properties = MAPPER.createJSONObject();
        for (Question question : questions) {
            properties.putPOJO(QUESTION_ID + question.getId(), createPropertyContent(question));
        }
        schema.putPOJO("properties", properties);

        return schema;
    }

    private static JSONObject createPropertyContent(Question question) throws IOException {
        return MAPPER.readTree(question.getJsonSchema());
    }

    private static JSONObject createUiSchema(List<Question> questions) {
        JSONObject schema = new JSONObject();
        questions.forEach(question -> schema.put(QUESTION_ID + question.getId(), createUiSchema(question)));
        return schema;
    }

    private static JSONObject createUiSchema(Question question) {
        JSONObject ui = new JSONObject();
        ui.put(DISABLED, false);

        if (question.getAnswerType().equals(AnswerType.RADIO_BUTTON)) {
            ui.put(WIDGET, "radio");
        } else if (question.getAnswerType().equals(AnswerType.SLIDER)) {
            ui.put(WIDGET, "slider");
        }

        return ui;
    }
}
