package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.TrialRole;

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
