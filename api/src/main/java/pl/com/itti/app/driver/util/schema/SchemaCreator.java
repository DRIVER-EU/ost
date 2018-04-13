package pl.com.itti.app.driver.util.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.model.enums.AnswerType;

import java.util.List;

public final class SchemaCreator {

    private static final String DISABLED = "ui:disabled";

    private static final String WIDGET = "ui:widget";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static ObjectNode createSchemaForm(List<Question> questions) {
        ObjectNode schemaForm = MAPPER.createObjectNode();

        ObjectNode schema = createSchema(questions);
        schemaForm.putPOJO("schema", schema);

        ObjectNode uiSchema = createUiSchema(questions);
        schemaForm.putPOJO("uiSchema", uiSchema);

        return schemaForm;
    }

    private static ObjectNode createSchema(List<Question> questions) {
        ObjectNode schema = MAPPER.createObjectNode();
        schema.put("type", "object");

        ObjectNode properties = MAPPER.createObjectNode();
        questions.forEach(question -> properties.putPOJO("question_" + question.getId(), createPropertyContent(question)));
        schema.putPOJO("properties", properties);

        return schema;
    }

    private static ObjectNode createPropertyContent(Question question) {
        ObjectNode property = MAPPER.createObjectNode();
        property.put("type", question.getAnswerType().getType());
        property.put("title", question.getName());
        property.put("description", question.getDescription());

        switch (question.getAnswerType()) {
            case SLIDER:
                property.put("min", 1);
                property.put("max", 10);
                property.put("value", 2);
                property.put("step", 1);
                break;
            case CHECKBOX:
                break;
            case TEXT_FIELD:
                break;
            case RADIO_BUTTON:
                break;
        }
        return property;
    }

    private static ObjectNode createUiSchema(List<Question> questions) {
        ObjectNode schema = MAPPER.createObjectNode();

        questions.forEach(question -> {
            ObjectNode ui = MAPPER.createObjectNode();
            ui.put(DISABLED, false); // TODO if something then it's true

            if (question.getAnswerType().equals(AnswerType.RADIO_BUTTON)) {
                ui.put(WIDGET, "Radio");
            } else if (question.getAnswerType().equals(AnswerType.SLIDER)) {
                ui.put(WIDGET, "Slider");
            }

            schema.putPOJO("question_" + question.getId(), ui);
        });

        return schema;
    }
}
