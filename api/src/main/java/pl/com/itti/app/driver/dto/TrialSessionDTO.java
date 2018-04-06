package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;

public class TrialSessionDTO {

    public static class MinimalItem implements EntityDTO<TrialSession> {
        public long id;
        public SessionStatus status;

        public MinimalItem(TrialSession trialSession) {
            this.id = trialSession.getId();
            this.status = trialSession.getStatus();
        }

        @Override
        public void toDto(TrialSession trialSession) {
            this.id = trialSession.getId();
        }
    }

    public static class FormItem extends MinimalItem {
        public String trialName;
        public String trialDescription;

        public FormItem(TrialSession trialSession) {
            super(trialSession);
            this.trialName = trialSession.getTrial().getName();
            this.trialDescription = trialSession.getTrial().getDescription();
        }
    }
}
