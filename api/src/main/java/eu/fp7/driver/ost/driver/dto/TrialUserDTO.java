package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import lombok.Data;

public final class TrialUserDTO {

    public static class ListItem implements EntityDto<TrialUser> {

        public long id;
        public String keycloakUserId;

        @Override
        public void toDto(TrialUser trialUser) {
            this.id = trialUser.getId();
            this.keycloakUserId = trialUser.getKeycloakUserId();
        }
    }

    public static class AdminEditItem extends ListItem {

        @Override
        public void toDto(TrialUser trialUser) {
            super.toDto(trialUser);
        }
    }
    @Data
    public static class AdminEditItemStatistics extends ListItem {

        public String email;
        public String login;
        public String trialRoleName;

        public TrialSessionDTO.ListOfActiveSessions activeSession = new TrialSessionDTO.ListOfActiveSessions();

        public void toDto(UserRoleSession userRoleSession) {
            TrialUser trialUser = userRoleSession.getTrialUser();
            super.toDto(trialUser);
            this.activeSession.toDto(userRoleSession);
            this.trialRoleName  = userRoleSession.getTrialRole().getName();

        }
    }



    public TrialUserDTO() {
        throw new AssertionError();
    }
}
