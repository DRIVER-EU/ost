package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.ObservationTypeTrialRole;
import eu.fp7.driver.ost.driver.model.TrialRole;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
        public List<ObservationTypeDTO.ListItem> questions = new ArrayList<>();
        @Override
        public void toDto(TrialRole trialRole) {
            super.toDto(trialRole);
            this.trialId = trialRole.getTrial().getId();

            for(ObservationTypeTrialRole observationTypeTrialRole : trialRole.getObservationTypeTrialRoles())
            {
                ObservationTypeDTO.ListItem observationTypeDTO = new ObservationTypeDTO.ListItem();

                observationTypeDTO.toDto(observationTypeTrialRole.getObservationType());
                questions.add(observationTypeDTO);
            }
        }
    }

    private TrialRoleDTO() {
        throw new AssertionError();
    }
}
