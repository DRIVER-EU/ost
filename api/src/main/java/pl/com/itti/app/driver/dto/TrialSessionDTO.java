package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TrialSessionDTO {

    public static class MinimalItem implements EntityDTO<TrialSession> {
        public long id;
        public SessionStatus status;

        @Override
        public void toDto(TrialSession trialSession) {
            this.id = trialSession.getId();
            this.status = trialSession.getStatus();
        }
    }

    public static class ListItem extends MinimalItem {
        public String trialName;
        public String trialDescription;

        public void toDto(TrialSession trialSession) {
            super.toDto(trialSession);
            this.trialName = trialSession.getTrial().getName();
            this.trialDescription = trialSession.getTrial().getDescription();
        }
    }

    public static class FullItem extends MinimalItem {

        public long trialId;
        public Long lastTrialStageId;
        public LocalDateTime startTime;
        public LocalDateTime pausedTime;
        public SessionStatus status;

        @Override
        public void toDto(TrialSession trialSession) {
            super.toDto(trialSession);
            this.trialId = trialSession.getTrial().getId();
            this.lastTrialStageId = trialSession.getLastTrialStage() != null ? trialSession.getLastTrialStage().getId() : null;
            this.startTime = trialSession.getStartTime();
            this.pausedTime = trialSession.getPausedTime();
        }


    }
}
