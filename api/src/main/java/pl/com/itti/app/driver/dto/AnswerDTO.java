package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.databind.JsonNode;
import pl.com.itti.app.driver.model.Answer;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class AnswerDTO {

    public static class Item implements EntityDTO<Answer> {

        @Override
        public void toDto(Answer answer) {

        }
    }

    public static class Form {

        @NotNull
        public Long observationTypeId;

        @NotNull
        public Long trialSessionId;

        public ZonedDateTime simulationTime;

        @NotNull
        public String fieldValue;

        @NotNull
        public JsonNode formData;
    }

    private AnswerDTO() {
        throw new AssertionError();
    }
}
