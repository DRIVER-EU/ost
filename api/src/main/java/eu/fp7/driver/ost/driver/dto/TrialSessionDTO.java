package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.enums.SessionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrialSessionDTO {

    public static class MinimalItem implements EntityDto<TrialSession> {

        public long id;
        public SessionStatus status;

        @Override
        public void toDto(TrialSession trialSession) {
            this.id = trialSession.getId();
            this.status = trialSession.getStatus();
        }
    }
    @Data
    public static class AdminEditItem extends MinimalItem {

        public long trialId;
        public String trialName;
        public String trialDescription;
        public long lastTrialStageId;
        public String lastTrialStageName;
        public List<AdminTrialStageDTO.ListItem> stages= new ArrayList<>();
        public List<AdminUserRoleDTO.ListItem> userRoles= new ArrayList<>();
        public List<TrialUserDTO.AdminEditItem> trialUsers= new ArrayList<>();
        public List<SessionStatus> statuses= SessionStatus.getStatuses();


        public void toDto(TrialSession trialSession) {
            super.toDto(trialSession);
            this.trialId = trialSession.getTrial().getId();
            this.trialName = trialSession.getTrial().getName();
            this.trialDescription = trialSession.getTrial().getDescription();
            this.lastTrialStageId = trialSession.getLastTrialStage().getId();
            lastTrialStageName = trialSession.getLastTrialStage().getName();
            for (TrialStage trialStage:  trialSession.getLastTrialStage().getTrial().getTrialStages()) {
                AdminTrialStageDTO.ListItem trialStageDTO =  new AdminTrialStageDTO.ListItem();
                trialStageDTO.toDto(trialStage);
                stages.add(trialStageDTO);
            }
            for (UserRoleSession userRoleSession:  trialSession.getUserRoleSessions()) {

                AdminUserRoleDTO.ListItem adminUserRoleDTO =  new AdminUserRoleDTO.ListItem();
                adminUserRoleDTO.toDto(userRoleSession);
                userRoles.add(adminUserRoleDTO);
            }

            for (UserRoleSession userRoleSession:  trialSession.getUserRoleSessions()) {

                TrialUserDTO.AdminEditItem trialUserDTO =  new TrialUserDTO.AdminEditItem();
                trialUserDTO.toDto(userRoleSession.getTrialUser());
                trialUsers.add(trialUserDTO);
            }

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
