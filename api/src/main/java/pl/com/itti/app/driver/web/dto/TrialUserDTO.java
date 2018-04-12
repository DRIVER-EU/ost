package pl.com.itti.app.driver.web.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialUser;

public class TrialUserDTO {

    public static class MinimalItem implements EntityDTO<TrialUser> {
        public long id;

        @Override
        public void toDto(TrialUser trialUser) {
            this.id = trialUser.getId();
        }
    }
}
