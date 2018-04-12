package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialUser;

public class TrialUserDTO {

    public static class MinimalItem implements EntityDTO<TrialUser> {
        public long id;
        public String firstName;
        public String lastName;

        public MinimalItem(TrialUser trialUser) {
            this.id = trialUser.id;
            this.firstName = trialUser.getAuthUser().getFirstName();
            this.lastName = trialUser.getAuthUser().getLastName();
        }

        @Override
        public void toDto(TrialUser trialUser) {
            this.id = trialUser.id;
            this.firstName = trialUser.getAuthUser().getFirstName();
            this.lastName = trialUser.getAuthUser().getLastName();
        }
    }
}
