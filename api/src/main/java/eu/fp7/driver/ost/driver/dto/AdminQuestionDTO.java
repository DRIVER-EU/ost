package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.Question;
import eu.fp7.driver.ost.driver.model.QuestionOption;
import eu.fp7.driver.ost.driver.model.enums.AnswerType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public final class AdminQuestionDTO {
    @Data
    public static class ListItem implements EntityDto<Question> {

        public long id;
        public String name;
        public String description;
        public int position;
        public long observationTypeId;
        public AnswerType answerType;


        @Override
        public void toDto(Question question) {
            this.id = question.getId();
            this.name = question.getName();
            this.description = question.getDescription();
            this.position = question.getPosition();
            this.answerType = question.getAnswerType();
            if(question.getObservationType() !=null) {
                this.observationTypeId = question.getObservationType().getId();
            }
        }
    }
    @Data
    public static class FullItem extends ListItem {


        public String jsonSchema;
        public boolean commented;
        private List<AdminQuestionOptionDTO.ListItem> questionOptions = new ArrayList<>();


        @Override
        public void toDto(Question question) {
            super.toDto(question);


            this.jsonSchema = question.getJsonSchema();
            this.commented = question.isCommented();
            for( QuestionOption questionOption :question.getQuestionOptions())
            {
                AdminQuestionOptionDTO.ListItem item = new AdminQuestionOptionDTO.ListItem();
                item.toDto(questionOption);
                questionOptions.add(item);
            }
        }
    }

    private AdminQuestionDTO() {
        throw new AssertionError();
    }
}
