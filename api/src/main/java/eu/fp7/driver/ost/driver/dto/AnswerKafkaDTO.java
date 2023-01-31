package eu.fp7.driver.ost.driver.dto;

public class AnswerKafkaDTO {
    Long timeMs = 0L;
    String questionName = "";
    String questionDescription = "";
    String answerType;
    String answers;
    String userRole;
    String userLogin;

    public AnswerKafkaDTO(){}

    public AnswerKafkaDTO(String questionName, String questionDescription, String answerType, String answers,
                          String userRole, String userLogin, Long time) {
        this.questionName = questionName;
        this.questionDescription = questionDescription;
        this.answerType = answerType;
        this.answers = answers;
        this.userRole = userRole;
        this.userLogin = userLogin;
        this.timeMs = time;
    }

    public void setAnswers(String answers){
        this.answers = answers;
    }

    public void setTimeMs(Long timeMs){
        this.timeMs = timeMs;
    }

    public void setQuestionName(String questionName){
        this.questionName = questionName;
    }

    public void setQuestionDescription(String questionDescription){
        this.questionDescription = questionDescription;
    }

    public void setAnswerType(String answerType){
        this.answerType = answerType;
    }

    public void setUserRole(String userRole){
        this.userRole = userRole;
    }

    public void setUserLogin(String userLogin){
        this.userLogin = userLogin;
    }

    public String getQuestionName(){
        return this.questionName;
    }

    public String getAnswerType(){
        return this.answerType;
    }

    public String getQuestionDescription(){
        return this.questionDescription;
    }

    public String getAnswers(){
        return this.answers;
    }

    public String getUserRole(){
        return this.userRole;
    }

    public String getUserLogin(){
        return this.userLogin;
    }

    public Long getTimeMs(){
        return this.timeMs;
    }
}
