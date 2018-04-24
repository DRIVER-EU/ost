package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialStage;

import java.time.LocalDateTime;

public final class TrialStageDTO {

    public static class ListItem implements EntityDTO<TrialStage> {

        public long id;
        public long trialId;
        public String name;
        public LocalDateTime simulationTime;

        @Override
        public void toDto(TrialStage trialStage) {
            this.id = trialStage.getId();
            this.trialId = trialStage.getTrial().getId();
            this.name = trialStage.getName();
            this.simulationTime = trialStage.getSimulationTime();
        }
    }

    private TrialStageDTO() {
        throw new AssertionError();
    }
}
