package eu.fp7.driver.ost.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.model.UserRoleSession;
import eu.fp7.driver.ost.driver.model.UserRoleSessionId;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class AdminUserRoleDTO {
    @Data
    public static class ListItem implements EntityDto<UserRoleSession> {

        @JsonFormat
        private UserRoleSessionId id;
        @JsonFormat
        private long trialUserId;
        @JsonFormat
        private long trialRoleId;
        @JsonFormat
        private long trialSessionId;
        @JsonFormat
        private String trialUserName;
        @JsonFormat
        private String trialRoleName;


        @Override
        public void toDto(UserRoleSession userRoleSession) {
            this.id = userRoleSession.getId();
            this.trialUserId = userRoleSession.getTrialUser().getId();
            this.trialUserName =  userRoleSession.getTrialUser().getAuthUser().getFirstName() + " " +userRoleSession.getTrialUser().getAuthUser().getLastName();
            this.trialRoleId = userRoleSession.getTrialRole().getId();
            this.trialRoleName = userRoleSession.getTrialRole().getName();
            this.trialSessionId = userRoleSession.getTrialSession().getId();
        }
    }
    @Data
    public static class FullItem extends ListItem {

        @JsonFormat
        private List<TrialRoleDTO.ListItem> roleSet = new ArrayList<>();

        @Override
        public void toDto(UserRoleSession userRoleSession) {
            super.toDto(userRoleSession);
            for (TrialRole trialRole : userRoleSession.getTrialSession().getTrial().getTrialRoles()) {
                TrialRoleDTO.ListItem trialRoleDTO = new TrialRoleDTO.ListItem();
                trialRoleDTO.toDto(trialRole);
                roleSet.add(trialRoleDTO);
            }
        }
    }

}
