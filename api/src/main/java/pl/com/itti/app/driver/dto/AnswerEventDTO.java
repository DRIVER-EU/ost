package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class AnswerEventDTO {

    private static final String ANSWER_TYPE = "ANSWER";
    private static final String EVENT_TYPE = "EVENT";

    public static abstract class Item {
        public long id;
        public long observationTypeId;
        public String name;
        public String description;
        public ZonedDateTime time;
        public ZonedDateTime trialTime;
        public String type;
    }

    public static class AnswerItem extends Item implements EntityDTO<Answer> {

        public String comment;

        @Override
        public void toDto(Answer answer) {
            this.id = answer.getId();
            this.observationTypeId = answer.getObservationType().getId();
            this.name = answer.getObservationType().getName();
            this.description = answer.getObservationType().getDescription();
            this.time = answer.getSentSimulationTime().atZone(ZoneId.systemDefault());
            this.trialTime = Optional.ofNullable(answer.getTrialTime()).orElse(LocalDateTime.now()).atZone(ZoneId.systemDefault());
            this.type = ANSWER_TYPE;
            this.comment = answer.getComment();
        }
    }

    public static class EventItem extends Item implements EntityDTO<Event> {

        @Override
        public void toDto(Event event) {
            this.id = event.getId();
            this.name = event.getName();
            this.description = event.getDescription();
            this.time = event.getEventTime().atZone(ZoneId.systemDefault());
            this.type = EVENT_TYPE;
        }
    }

    private AnswerEventDTO() {
        throw new AssertionError();
    }
}
