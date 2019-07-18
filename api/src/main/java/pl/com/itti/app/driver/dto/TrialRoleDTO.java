package pl.com.itti.app.driver.dto;

import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.driver.model.TrialRole;

public final class TrialRoleDTO {

    public static class ListItem implements EntityDto<TrialRole> {

        public long id;
        public String name;

        @Override
        public void toDto(TrialRole trialRole) {
            this.id = trialRole.getId();
            this.name = trialRole.getName();
        }
    }

    public static class FullItem extends ListItem {

        public String roleType;

        @Override
        public void toDto(TrialRole trialRole) {
            super.toDto(trialRole);
            this.roleType = trialRole.getRoleType().name();
        }
    }

    private TrialRoleDTO() {
        throw new AssertionError();
    }
}
