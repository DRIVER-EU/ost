package eu.fp7.driver.ost.driver.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.util.InternalServerException;
import eu.fp7.driver.ost.driver.util.schema.SchemaCreator;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public final class QuestionAnswerDTO {

    public static class MinimalItem implements EntityDto<Answer> {

        public long answerId;
        public String name;
        public String description;
        public String time;
        public String trialTime;

        @Override
        public void toDto(Answer answer) {
            this.answerId = answer.getId();
            this.time = answer.getSimulationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.trialTime = answer.getTrialTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            ObservationType observationType = answer.getObservationType();
            this.name = observationType.getName();
            this.description = observationType.getDescription();
        }
    }

    public static class FullItem extends MinimalItem {

        public JsonNode questionSchema;
        public JsonNode formData;
        public JsonNode attachments;
        public JsonNode trialRoles;

        @Override
        public void toDto(Answer answer) {
            super.toDto(answer);

            try {
                this.questionSchema = SchemaCreator.createSchemaForm(answer.getObservationType().getQuestions(), true);
                this.formData = new ObjectMapper().readTree(answer.getFormData());
                this.attachments = SchemaCreator.createAttachmentSchemaForm(answer.getAttachments());
                this.trialRoles = SchemaCreator.createTrialRolesSchemaForm(answer.getAnswerTrialRoles());
            } catch (IOException ioe) {
                throw new InternalServerException("Error in jsonSchema", ioe);
            }
        }
    }

    private QuestionAnswerDTO() {
        throw new AssertionError();
    }
}
