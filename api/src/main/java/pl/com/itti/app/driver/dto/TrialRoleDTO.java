package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.TrialRole;

public class TrialRoleDTO {

    public static class FullItem implements EntityDTO<TrialRole> {
        public long id;
        public String name;
        public String roleType;

        @Override
        public void toDto(TrialRole trialRole) {
            this.id = trialRole.getId();
            this.name = trialRole.getName();
            this.roleType = trialRole.getRoleType().name();
        }
    }
}
