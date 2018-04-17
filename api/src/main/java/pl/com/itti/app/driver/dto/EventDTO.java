package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialUser;

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

    public static class ListItem extends MinimalItem {
        public String name;
        public String description;
        public LocalDateTime eventTime;
        public long trialUserId;
        public String trialUserFirstName;
        public String trialUserLastName;
        public long trialRoleId;
        public String trialRoleName;

        @Override
        public void toDto(Event event) {
            super.toDto(event);
            this.name = event.getName();
            this.description = event.getDescription();
            this.eventTime = event.getEventTime();

            Optional<TrialUser> trialUserOptional = Optional.ofNullable(event.getTrialUser());
            this.trialUserId = trialUserOptional.map(TrialUser::getId).orElse(-1L);
            this.trialUserFirstName = trialUserOptional.map(t -> t.getAuthUser().getFirstName()).orElse(null);
            this.trialUserLastName = trialUserOptional.map(t -> t.getAuthUser().getLastName()).orElse(null);

            Optional<TrialRole> trialRoleOptional = Optional.ofNullable(event.getTrialRole());
            this.trialRoleId = trialRoleOptional.map(TrialRole::getId).orElse(-1L);
            this.trialRoleName = trialRoleOptional.map(TrialRole::getName).orElse(null);
        }
    }
}
