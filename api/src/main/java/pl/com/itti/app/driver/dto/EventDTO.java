package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.model.enums.Languages;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

public class EventDTO {

    public static class MinimalItem implements EntityDTO<Event> {
        public long id;

        @Override
        public void toDto(Event event) {
            this.id = event.getId();
        }
    }

    public static class FormItem extends MinimalItem {

        @NotNull
        public Long trialSessionId;

        @NotNull
        public String name;

        @NotNull
        public String description;

        @NotNull
        public LocalDateTime eventTime;

        public Languages languageVersion;
        public long trialUserId;
        public long trialRoleId;

        @Override
        public void toDto(Event event) {
            super.toDto(event);
            this.trialSessionId = event.getTrialSession().getId();
            this.name = event.getName();
            this.description = event.getDescription();
            this.languageVersion = event.getLanguageVersion();
            this.eventTime = event.getEventTime();

            Optional<TrialUser> trialUserOptional = Optional.ofNullable(event.getTrialUser());
            this.trialUserId = trialUserOptional.map(TrialUser::getId).orElse(-1L);

            Optional<TrialRole> trialRoleOptional = Optional.ofNullable(event.getTrialRole());
            this.trialRoleId = trialRoleOptional.map(TrialRole::getId).orElse(-1L);
        }
    }

    public static class ListItem extends FormItem {
        public String firstName;
        public String lastName;
        public String trialRoleName;

        @Override
        public void toDto(Event event) {
            super.toDto(event);

            Optional<TrialUser> trialUserOptional = Optional.ofNullable(event.getTrialUser());
            this.firstName = trialUserOptional.map(t -> t.getAuthUser().getFirstName()).orElse(null);
            this.lastName = trialUserOptional.map(t -> t.getAuthUser().getLastName()).orElse(null);

            Optional<TrialRole> trialRoleOptional = Optional.ofNullable(event.getTrialRole());
            this.trialRoleName = trialRoleOptional.map(TrialRole::getName).orElse(null);
        }
    }
}
