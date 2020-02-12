package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import lombok.Data;

public final class TrialUserDTO {

    public static class ListItem implements EntityDto<TrialUser> {

        public long id;
        public String firstName;
        public String lastName;

        @Override
        public void toDto(TrialUser trialUser) {
            this.id = trialUser.getId();
            this.firstName = trialUser.getAuthUser().getFirstName();
            this.lastName = trialUser.getAuthUser().getLastName();
        }
    }

    public static class AdminEditItem extends ListItem {

        public String email;
        public String login;

        @Override
        public void toDto(TrialUser trialUser) {
            super.toDto(trialUser);
            this.email = trialUser.getAuthUser().getEmail();
            this.login = trialUser.getAuthUser().getLogin();
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
            this.email = trialUser.getAuthUser().getEmail();
            this.login = trialUser.getAuthUser().getLogin();
            this.activeSession.toDto(userRoleSession);
            this.trialRoleName  = userRoleSession.getTrialRole().getName();

        }
    }



    private TrialUserDTO() {
        throw new AssertionError();
    }
}
