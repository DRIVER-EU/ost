package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.model.enums.Languages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

public final class EventDTO {

    public static class MinimalItem implements EntityDTO<Event> {

        public long id;

        @Override
        public void toDto(Event event) {
            this.id = event.getId();
        }
    }

    public static class Item extends MinimalItem {

        public long trialSessionId;
        public long trialUserId;
        public long trialRoleId;
        public String name;
        public String description;
        public Languages languageVersion;

        @Override
        public void toDto(Event event) {
            super.toDto(event);
            this.trialSessionId = event.getTrialSession().getId();
            this.trialUserId = event.getTrialUser() != null ? event.getTrialUser().getId() : -1L;
            this.trialRoleId = event.getTrialRole() != null ? event.getTrialRole().getId() : -1L;
            this.name = event.getName();
            this.description = event.getDescription();
            this.languageVersion = event.getLanguageVersion();
        }
    }

    public static class ListItem extends Item {

        public String firstName;
        public String lastName;
        public String trialRoleName;

        @Override
        public void toDto(Event event) {
            super.toDto(event);
            this.trialRoleName = Optional.ofNullable(event.getTrialRole()).map(TrialRole::getName).orElse(null);

            Optional<TrialUser> trialUserOptional = Optional.ofNullable(event.getTrialUser());
            this.firstName = trialUserOptional.map(t -> t.getAuthUser().getFirstName()).orElse(null);
            this.lastName = trialUserOptional.map(t -> t.getAuthUser().getLastName()).orElse(null);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FormItem {

        @NotNull
        public Long trialSessionId;

        @NotNull
        @Size(min = 1, max = 50)
        public String name;

        @NotNull
        public String description;

        public Languages languageVersion;
        public long trialUserId;
        public long trialRoleId;
    }

    private EventDTO() {
        throw new AssertionError();
    }
}
