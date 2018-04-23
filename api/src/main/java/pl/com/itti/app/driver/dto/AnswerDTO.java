package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.databind.JsonNode;
import pl.com.itti.app.driver.model.Answer;

import java.time.ZonedDateTime;

public class AnswerDTO {

    public static class Item implements EntityDTO<Answer> {

        @Override
        public void toDto(Answer answer) {

        }
    }

    public static class Form {
        public long observationTypeId;
        public long trialSessionId;
        public ZonedDateTime sentSimulationTime;

        public JsonNode data;
    }

    private AnswerDTO() {
        throw new AssertionError();
    }
}
