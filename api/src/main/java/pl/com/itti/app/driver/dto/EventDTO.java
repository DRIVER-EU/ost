package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.TrialSession;

import java.time.LocalDateTime;

public class EventDTO {

    public static class MinimalItem implements EntityDTO<Event> {
        public long id;

        public MinimalItem(Event event) {
            this.id = event.getId();
        }

        @Override
        public void toDto(Event event) {
            this.id = event.getId();
        }
    }

    public static class ListItem extends MinimalItem {
        public String name;
        public String description;
        public LocalDateTime eventTime;

        public ListItem(Event event) {
            super(event);
            this.name = event.getName();
            this.description = event.getDescription();
            this.eventTime = event.getEventTime();
        }
    }
}
