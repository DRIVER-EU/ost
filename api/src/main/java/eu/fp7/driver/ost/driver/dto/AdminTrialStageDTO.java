package eu.fp7.driver.ost.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.TrialStage;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AdminTrialStageDTO {
    @Data
    public static class ListItem implements EntityDto<TrialStage> {
        @JsonFormat
        private long id;
        @JsonFormat
        private long trialId;
        @JsonFormat
        private String name;

        @Override
        public void toDto(TrialStage trialStage) {
            this.id = trialStage.getId();
            this.trialId = trialStage.getTrial().getId();
            this.name = trialStage.getName();
        }
    }

        @Data
        public static class FullItem extends AdminTrialStageDTO.ListItem {
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime simulationTime;
            @JsonFormat
            private List<AdminObservationTypeDTO.ListItem> questions = new ArrayList<>();
            @Override
            public void toDto(TrialStage trialStage) {
                super.toDto(trialStage);
                this.simulationTime = trialStage.getSimulationTime();
                for (ObservationType observationType : trialStage.getObservationTypes()) {
                    AdminObservationTypeDTO.ListItem question = new AdminObservationTypeDTO.ListItem();
                    question.toDto(observationType);
                    questions.add(question);
                }
            }
        }
    }

