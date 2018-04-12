package pl.com.itti.app.driver.util.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.com.itti.app.driver.model.Question;

import java.util.List;

public final class SchemaCreator {

    public static ObjectNode createSchemaForm(List<Question> questions) {
        ObjectNode schemaForm = new ObjectMapper().createObjectNode();

        ObjectNode schema = createSchema(questions);
        schemaForm.putPOJO("schema", schema);

//        ObjectNode uiSchema = createUiSchema(observationType);
//        schemaForm.putPOJO("uiSchema", uiSchema);
//
        return schemaForm;
    }

    private static ObjectNode createSchema(List<Question> questions) {
        ObjectNode schema = new ObjectMapper().createObjectNode();
        schema.put("type", "object");

        ObjectNode properties = new ObjectMapper().createObjectNode();
        questions.forEach(question -> properties.putPOJO("question_" + question.getId(), createPropertyContent(question)));
        schema.putPOJO("properties", properties);

        return schema;
    }

    private static ObjectNode createPropertyContent(Question question) {
        ObjectNode property = new ObjectMapper().createObjectNode();
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

//    private static ObjectNode createUiSchema(List<Question> questions) {
//
//    }
}
