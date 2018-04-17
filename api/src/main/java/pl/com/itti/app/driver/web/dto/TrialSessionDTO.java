package pl.com.itti.app.driver.web.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;

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
}
