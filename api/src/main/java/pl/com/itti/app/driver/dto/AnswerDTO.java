package pl.com.itti.app.driver.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class AnswerDTO {

    public static class Item {

    }

    public static class Form {
        public JsonNode data;
    }

    private AnswerDTO() {
        throw new AssertionError();
    }
}
