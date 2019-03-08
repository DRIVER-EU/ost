package pl.com.itti.app.driver.util;

import eu.driver.adapter.core.CISAdapter;
import eu.driver.adapter.excpetion.CommunicationException;
import eu.driver.examples.adapter.TestSchemaProducer;
import eu.driver.model.core.ObserverToolAnswer;
import ly.stealth.xmlavro.DatumBuilder;
import org.apache.avro.generic.GenericRecord;
import org.json.JSONObject;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.AnswerType;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

public class SendToTestBed {

    public static void sendToTestBed(Answer answer, ObservationType observationType, TrialSession trialSession){
        int trialId = Math.toIntExact(trialSession.getTrial().getId());
        int sessionId = Math.toIntExact(trialSession.getId());
        int answerId = Math.toIntExact(answer.getId());
        Long timeSendUTC = answer.getSentSimulationTime().atZone(ZoneId.of("UTC")).toEpochSecond();
        Long timeWhen = answer.getSimulationTime().toInstant().toEpochMilli();
        String observationTypeName = observationType.getName();
        int observationTypeId = Math.toIntExact(observationType.getId());
        String observationTypeDescription = observationType.getDescription();
        //TODO: What is that description below?
        String description = answer.getAttachments().get(0).getDescription();
        boolean multiplicity = observationType.isMultiplicity();

        String questionsXml="";

        List<Question> questionList = answer.getObservationType().getQuestions();
        for (Question question : answer.getObservationType().getQuestions()){
            int questionId = Math.toIntExact(question.getId());
            String questionName = question.getName();
            String questionDescr = question.getDescription();
            String questionAnswer = SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId());
            String questionComment = SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY);
            AnswerType questionEnumTypeOfAnswer = question.getAnswerType();
            String questionTypeOfAnswer="";
            switch (questionEnumTypeOfAnswer) {
                case CHECKBOX:
                    questionTypeOfAnswer = "checkbox";
                    break;
                case RADIO_BUTTON:
                    questionTypeOfAnswer = "radiobutton";
                    break;
                case SLIDER:
                    questionTypeOfAnswer = "slider";
                    break;
                case TEXT_FIELD:
                    questionTypeOfAnswer = "text";
                    break;
            }

            questionsXml = questionsXml+"<id>"+questionId+"</id>\n"+
                                "<name>"+questionName+"</name>\n"+
                                "<description>"+questionDescr+"</description>\n"+
                                "<answer>"+questionAnswer+"</answer>\n"+
                                "<comment>"+questionComment+"</comment>\n"+
                                "<typeOfQuestion>"+questionTypeOfAnswer+"</typeOfQuestion>\n";
            }


        String xmlInString =
        "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n" +
        "<alert xmlns=\"urn:oasis:names:tc:emergency:cap:1.2\">\n" +
        "    <trialId>"+trialId+"</trialId>\n" +
        "    <sessionId>"+sessionId+"</sessionId>\n" +
        "    <answerId>"+answerId+"</answerId>\n" +
        "    <timeSendUTC>"+timeSendUTC+"</timeSendUTC>\n" +
        "    <timeWhen>"+timeWhen+"</timeWhen>\n" +
        "    <observationTypeName>"+observationTypeName+"</observationTypeName>\n" +
        "    <observervationTypeId>"+observationTypeId+"</observervationTypeId>\n" +
        "    <observationTypeDescription>"+observationTypeDescription+"</observationTypeDescription>\n" +
        "    <description>"+description+"</description>\n" +
        "    <multiplicity>"+multiplicity+"</multiplicity>\n" +
        "    <questions>\n" +questionsXml+
        "    </questions>\n" +
        "</alert>";


        String topic = "system_observer_tool_answer";
        CISAdapter adapter = CISAdapter.getInstance();
        adapter.createProducer(topic);
        try {
            adapter.sendMessage(generateAvroFromXML(xmlInString), topic);
        } catch (CommunicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static GenericRecord generateAvroFromXML(String XML){
        DatumBuilder datumBuilder = new DatumBuilder(ObserverToolAnswer.getClassSchema());

        GenericRecord datum = datumBuilder.createDatum(XML);

        System.out.println("Wartosc: " + datum.get("trialId"));

        return datum;
    }

}
