package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.QuestionOption;
import lombok.Data;

@Data
public final class AdminQuestionOptionDTO {
    @Data
    public static class ListItem implements EntityDto<QuestionOption> {

        public long id;
        public String name;

        @Override
        public void toDto(QuestionOption questionOption) {
            this.id = questionOption.getId();
            this.name = questionOption.getName();
        }
    }
    @Data
    public static class FullItem extends ListItem {
        public int position;
        public long questionId;
        @Override
        public void toDto(QuestionOption questionOption) {
            super.toDto(questionOption);
            this.position = questionOption.getPosition();
            if(questionOption.getQuestion() !=null) {
                this.questionId =  questionOption.getQuestion().getId();
            }

        }
    }

    private AdminQuestionOptionDTO() {
        throw new AssertionError();
    }
}
