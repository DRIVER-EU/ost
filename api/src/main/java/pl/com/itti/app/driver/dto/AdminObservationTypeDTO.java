package pl.com.itti.app.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.Question;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminObservationTypeDTO {
    @Data
    public static class ListItem implements EntityDto<ObservationType> {
        @JsonFormat
        private long id;
        @JsonFormat
        private String name;
        @JsonFormat
        private String description;

        @Override
        public void toDto(ObservationType observationType) {
            this.id = observationType.getId();
            this.name = observationType.getName();
            this.description = observationType.getDescription();
        }
    }
    @Data
    public static class FullItem extends ListItem {
        @JsonFormat
        private long trailStageId;
        @JsonFormat
        private long trailId;
        @JsonFormat
        private boolean multiplicity;
        @JsonFormat
        private boolean withUsers;
        @JsonFormat
        private int position;
        @JsonFormat
        private  List<AdminQuestionDTO.ListItem> questions = new ArrayList<>();

        @Override
        public void toDto(ObservationType observationType) {
            super.toDto(observationType);
            for( Question question :observationType.getQuestions())
            {
               AdminQuestionDTO.ListItem item = new AdminQuestionDTO.ListItem();
               item.toDto(question);
                questions.add(item);
            }
            this.trailStageId = observationType.getTrialStage().getId();
            this.trailId = observationType.getTrial().getId();
            boolean multiplicity = observationType.isMultiplicity();
            boolean withUsers = observationType.isWithUsers();
            int position = observationType.getPosition();
        }
    }

}