package pl.com.itti.app.driver.model.enums;

public enum AnswerType {
    CHECKBOX("boolean"),
    RADIO_BUTTON("boolean"),
    SLIDER("integer"),
    TEXT_FIELD("string");

    private String type;

    AnswerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
