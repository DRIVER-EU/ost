package pl.com.itti.app.driver.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.core.dto.FormDto;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.AnswerTrialRole;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.ObservationTypeTrialRole;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.UserRoleSession;
import pl.com.itti.app.driver.util.InternalServerException;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class AnswerDTO {

    public static class MinimalItem implements EntityDto<Answer> {

        public long id;
        public ZonedDateTime sentSimulationTime;

        @Override
        public void toDto(Answer answer) {
            this.id = answer.getId();
            this.sentSimulationTime = answer.getSentSimulationTime().atZone(ZoneId.systemDefault());
        }
    }

    public static class Item extends MinimalItem {

        public long trialSessionId;
        public long trialUserId;
        public long observationTypeId;
        public ZonedDateTime simulationTime;
        public String fieldValue;
        public JsonNode formData;
        public List<AttachmentDTO.Item> attachments;
        public List<TrialRoleDTO.ListItem> trialRoles;

        @Override
        public void toDto(Answer answer) {
            super.toDto(answer);
            this.trialSessionId = answer.getTrialSession().getId();
            this.trialUserId = answer.getTrialUser().getId();
            this.observationTypeId = answer.getObservationType().getId();
            this.simulationTime = answer.getSimulationTime();
            this.fieldValue = answer.getFieldValue();
            try {
                this.formData = new ObjectMapper().readTree(answer.getFormData());
            } catch (IOException ioe) {
                throw new InternalServerException("Error in json form", ioe);
            }
            this.attachments = Dto.from(answer.getAttachments(), AttachmentDTO.Item.class);
            this.trialRoles = Dto.from(
                    answer.getAnswerTrialRoles().stream().map(AnswerTrialRole::getTrialRole).collect(Collectors.toList()),
                    TrialRoleDTO.ListItem.class
            );
        }
    }

    public static class ListItem extends MinimalItem {

        public TrialUserDTO.ListItem user;
        public String roleName;
        public String observationTypeName;
        public String observationTypeDescription;

        @Override
        public void toDto(Answer answer) {
            super.toDto(answer);
            this.user = Dto.from(answer.getTrialUser(), TrialUserDTO.ListItem.class);

            List<TrialRole> trialRoles = answer.getObservationType().getObservationTypeTrialRoles().stream()
                    .map(ObservationTypeTrialRole::getTrialRole)
                    .collect(Collectors.toList());
            Optional<TrialRole> trialRole = answer.getTrialUser().getUserRoleSessions().stream()
                    .map(UserRoleSession::getTrialRole)
                    .filter(trialRoles::contains)
                    .findFirst();
            trialRole.ifPresent(trialRole1 -> this.roleName = Dto.from(trialRole1, TrialRoleDTO.ListItem.class).name);

            ObservationType observationType = answer.getObservationType();
            this.observationTypeName = observationType.getName();
            this.observationTypeDescription = observationType.getDescription();
        }
    }

    public static class Form implements FormDto {

        @NotNull
        public Long observationTypeId;

        @NotNull
        public Long trialSessionId;

        @NotNull
        public ZonedDateTime simulationTime;

        public String fieldValue;

        @NotNull
        public JsonNode formData;

        public List<Long> trialRoleIds;

        public List<AttachmentDTO.Coordinates> coordinates;

        public List<String> descriptions;

        public String comment;
    }

    private AnswerDTO() {
        throw new AssertionError();
    }
}
