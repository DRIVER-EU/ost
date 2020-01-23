package eu.fp7.driver.ost.driver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.TrialStage;
import lombok.Data;

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
