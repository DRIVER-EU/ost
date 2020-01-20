package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialStage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AdminTrialStageDTO {
    @Data
    public static class ListItem implements EntityDTO<TrialStage> {
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

