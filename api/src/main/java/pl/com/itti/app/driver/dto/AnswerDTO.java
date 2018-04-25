package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.IOException;

public final class AnswerDTO {

    public static class MinimalItem implements EntityDTO<Answer> {

        public long id;

        @Override
        public void toDto(Answer answer) {
            this.id = answer.getId();
        }
    }

    public static class SchemaItem extends MinimalItem {

        public JsonNode questionSchema;
        public JsonNode formData;

        @Override
        public void toDto(Answer answer) {
            super.toDto(answer);

            try {
                this.questionSchema = SchemaCreator.createSchemaForm(answer.getObservationType().getQuestions(), true);
                this.formData = new ObjectMapper().readTree(answer.getFormData());
            } catch (IOException ioe) {
                throw new InternalServerException("Error in jsonSchema", ioe);
            }
        }
    }

    private AnswerDTO() {
        throw new AssertionError();
    }
}
