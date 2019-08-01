package pl.com.itti.app.driver.dto;

import co.perpixel.db.model.PersistentObject;
import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.model.enums.SessionStatus;

import java.time.LocalDateTime;
import java.util.Optional;

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

        public long trialId;
        public String trialName;
        public String trialDescription;
        public String lastTrialStage;

        public void toDto(TrialSession trialSession) {
            super.toDto(trialSession);
            this.trialId = trialSession.getTrial().getId();
            this.trialName = trialSession.getTrial().getName();
            this.trialDescription = trialSession.getTrial().getDescription();
            this.lastTrialStage = trialSession.getLastTrialStage().getName();
        }
    }

    public static class ActiveListItem extends ListItem {

        public Long initId;
        public Boolean initHasAnswer;
        public String name;

        public void toDto(TrialSession trialSession) {
            super.toDto(trialSession);

            Optional<ObservationType> optionalInit = Optional.ofNullable(trialSession.getTrial().getInitId());
            this.initId = optionalInit.map(PersistentObject::getId).orElse(null);
            this.name = trialSession.getLastTrialStage().getName();
        }
    }

    public static class FullItem extends MinimalItem {

        public long trialId;
        public Long lastTrialStageId;
        public LocalDateTime startTime;
        public LocalDateTime pausedTime;

        @Override
        public void toDto(TrialSession trialSession) {
            super.toDto(trialSession);
            this.trialId = trialSession.getTrial().getId();

            Optional<TrialStage> optionalStage = Optional.ofNullable(trialSession.getLastTrialStage());
            this.lastTrialStageId = optionalStage.map(PersistentObject::getId).orElse(null);
            this.startTime = trialSession.getStartTime();
            this.pausedTime = trialSession.getPausedTime();
        }
    }
}
