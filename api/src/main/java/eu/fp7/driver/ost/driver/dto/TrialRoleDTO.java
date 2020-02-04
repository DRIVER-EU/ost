package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.TrialRole;
import lombok.Data;

public final class TrialRoleDTO {
    @Data
    public static class ListItem implements EntityDto<TrialRole> {

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
    @Data
    public static class FullItem extends ListItem {
        public long trialId;
        @Override
        public void toDto(TrialRole trialRole) {
            super.toDto(trialRole);
            this.trialId = trialRole.getTrial().getId();
        }
    }

    private TrialRoleDTO() {
        throw new AssertionError();
    }
}
