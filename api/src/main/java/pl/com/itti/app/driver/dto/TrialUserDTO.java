package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialUser;

public final class TrialUserDTO {

    public static class ListItem implements EntityDTO<TrialUser> {

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

    private TrialUserDTO() {
        throw new AssertionError();
    }
}