package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Event;

import java.time.LocalDateTime;

public final class AnswerEventDTO {

    private static final String ANSWER_TYPE = "ANSWER";
    private static final String EVENT_TYPE = "EVENT";

    public static abstract class Item {
        public long id;
        public String name;
        public String description;
        public LocalDateTime time;
        public String type;
    }

    public static class AnswerItem extends Item implements EntityDTO<Answer> {

        @Override
        public void toDto(Answer answer) {
            this.id = answer.getId();
            this.name = answer.getObservationType().getName();
            this.description = answer.getObservationType().getDescription();
            this.time = answer.getSentSimulationTime();
            this.type = ANSWER_TYPE;
        }
    }

    public static class EventItem extends Item implements EntityDTO<Event> {

        @Override
        public void toDto(Event event) {
            this.id = event.getId();
            this.name = event.getName();
            this.description = event.getDescription();
            this.time = event.getEventTime();
            this.type = EVENT_TYPE;
        }
    }

    private AnswerEventDTO() {
        throw new AssertionError();
    }
}
