package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialUser;

public final class TrialUserDTO {

    public static class MinimalItem implements EntityDTO<TrialUser> {
        public long id;

        @Override
        public void toDto(TrialUser trialUser) {
            this.id = trialUser.id;
        }
    }

    private TrialUserDTO() {
        throw new AssertionError();
    }
}
