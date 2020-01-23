package pl.com.itti.app.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.UserRoleSession;
import pl.com.itti.app.driver.model.UserRoleSessionId;

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
