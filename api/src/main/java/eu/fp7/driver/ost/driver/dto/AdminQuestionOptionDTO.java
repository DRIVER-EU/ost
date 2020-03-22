package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.QuestionOption;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public final class AdminQuestionOptionDTO {
    @Data
    public static class ListItem implements EntityDto<QuestionOption> {

        public long id;
        public String name;
        public int position;

        @Override
        public void toDto(QuestionOption questionOption) {
            this.id = questionOption.getId();
            this.name = questionOption.getName();
            this.position = questionOption.getPosition();
        }
    }
    @Data
    public static class FullItem extends ListItem {

        public long questionId;
        @Override
        public void toDto(QuestionOption questionOption) {
            super.toDto(questionOption);

            if(questionOption.getQuestion() !=null) {
                this.questionId =  questionOption.getQuestion().getId();
            }

        }
    }

    private AdminQuestionOptionDTO() {
        throw new AssertionError();
    }
}
