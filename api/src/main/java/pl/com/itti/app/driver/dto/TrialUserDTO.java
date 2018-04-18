package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialUser;

public class TrialUserDTO {

    public static class MinimalItem implements EntityDTO<TrialUser> {
        public long id;

        @Override
        public void toDto(TrialUser trialUser) {
            this.id = trialUser.id;
        }
    }

    public static class ListItem extends MinimalItem {
        public String firstName;
        public String lastName;

        @Override
        public void toDto(TrialUser trialUser) {
            super.toDto(trialUser);

            this.firstName = trialUser.getAuthUser().getFirstName();
            this.lastName = trialUser.getAuthUser().getLastName();
        }
    }
}
