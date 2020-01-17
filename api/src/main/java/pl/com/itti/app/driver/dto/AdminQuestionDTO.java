package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import lombok.Data;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.model.enums.AnswerType;
@Data
public final class AdminQuestionDTO {
    @Data
    public static class ListItem implements EntityDTO<Question> {

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
