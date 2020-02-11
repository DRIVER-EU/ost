package eu.fp7.driver.ost.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.Question;
import lombok.Data;

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
        @JsonFormat
        private int position;

        @Override
        public void toDto(ObservationType observationType) {
            this.id = observationType.getId();
            this.name = observationType.getName();
            this.description = observationType.getDescription();
            this.position = observationType.getPosition();
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
            this.multiplicity = observationType.isMultiplicity();
            this.withUsers = observationType.isWithUsers();

        }
    }

}