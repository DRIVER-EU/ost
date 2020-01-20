package pl.com.itti.app.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;

import java.util.ArrayList;
import java.util.List;


public class AdminTrialDTO  {
    @Data
    public static class ListItem implements EntityDto<Trial> {

        @JsonFormat
        private long trialId;
        @JsonFormat
        private String trialName;
        @JsonFormat
        private String trialDescription;
        @JsonFormat
        private String lastTrialStage;
        @JsonFormat
        private Boolean archived;


        @Override
        public void toDto(Trial trial) {
            this.trialId = trial.getId();
            this.trialName = trial.getName();
            this.trialDescription = trial.getDescription();
            this.archived = trial.getIsArchived();
        }
    }
    @Data
    public static class FullItem extends AdminTrialDTO.ListItem {

        @JsonFormat
        private List<TrialStageDTO.ListItem> stageSet = new ArrayList<>();
        @JsonFormat
        private List<TrialSessionDTO.ListItem> sessionSet = new ArrayList<>();
        @JsonFormat
        private List<TrialRoleDTO.ListItem>  roleSet = new ArrayList<>();

        @Override
        public void toDto(Trial trial) {
            super.toDto(trial);

            for (TrialSession trialSession:trial.getTrialSessions()) {
                TrialSessionDTO.ListItem trialSessionDTO =  new TrialSessionDTO.ListItem();
                trialSessionDTO.toDto(trialSession);
                sessionSet.add(trialSessionDTO);
            }

            for (TrialRole trialRole:trial.getTrialRoles()) {
                TrialRoleDTO.ListItem trialRoleDTO =  new TrialRoleDTO.ListItem();
                trialRoleDTO.toDto(trialRole);
                roleSet.add(trialRoleDTO);
            }

            for (TrialStage trialStage:trial.getTrialStages()) {
                TrialStageDTO.ListItem trialStageDTO =  new TrialStageDTO.ListItem();
                trialStageDTO.toDto(trialStage);
                stageSet.add(trialStageDTO);
            }
        }
    }
}
