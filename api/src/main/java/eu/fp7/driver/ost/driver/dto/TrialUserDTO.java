package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.TrialUser;

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

    private TrialUserDTO() {
        throw new AssertionError();
    }
}
