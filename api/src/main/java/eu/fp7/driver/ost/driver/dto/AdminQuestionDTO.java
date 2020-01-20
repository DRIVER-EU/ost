package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.Question;
import eu.fp7.driver.ost.driver.model.enums.AnswerType;
import lombok.Data;
@Data
public final class AdminQuestionDTO {
    @Data
    public static class ListItem implements EntityDto<Question> {

        public long id;
        public String name;
        public String description;
        public long observationTypeId;


        @Override
        public void toDto(Question question) {
            this.id = question.getId();
            this.name = question.getName();
            this.description = question.getDescription();
            if(question.getObservationType() !=null) {
                this.observationTypeId = question.getObservationType().getId();
            }
        }
    }
    @Data
    public static class FullItem extends ListItem {
        public AnswerType answerType;
        public int position;
        public String jsonSchema;
        public boolean commented;


        @Override
        public void toDto(Question question) {
            super.toDto(question);
            this.answerType = question.getAnswerType();
            this.position = question.getPosition();
            this.jsonSchema = question.getJsonSchema();
            this.commented = question.isCommented();
        }
    }

    private AdminQuestionDTO() {
        throw new AssertionError();
    }
}
