package pl.com.itti.app.driver.util;

import eu.driver.adapter.core.CISAdapter;
import eu.driver.adapter.excpetion.CommunicationException;
import eu.driver.model.core.ObserverToolAnswer;
import ly.stealth.xmlavro.DatumBuilder;
import org.apache.avro.generic.GenericRecord;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.AnswerType;
import pl.com.itti.app.driver.model.enums.AttachmentType;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

import static pl.com.itti.app.driver.util.SimulationTime.adapterInit;

public class SendToTestBed {

    public static void sendToTestBed(Answer answer, ObservationType observationType, TrialSession trialSession){

        String questionsXml="";

        for (Question question : answer.getObservationType().getQuestions()){

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

            questionsXml =
                    "<id>"+Math.toIntExact(question.getId())+"</id>\n"+
                    "<name>"+question.getName()+"</name>\n"+
                    "<description>"+question.getDescription()+"</description>\n"+
                    "<answer>"+SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId())+"</answer>\n"+
                    "<comment>"+SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY)+"</comment>\n"+
                    "<typeOfQuestion>"+questionTypeOfAnswer+"</typeOfQuestion>\n";

            String attachmentDescription = "";

            Optional<Attachment> attachment = answer.getAttachments().stream().filter(Objects::nonNull).findFirst();

            if (!attachment.isPresent()) attachmentDescription = "";
            else attachmentDescription = attachment.get().getDescription();

            String xmlInString =
                            "<alert xmlns=\"urn:oasis:names:tc:emergency:cap:1.2\">\n" +
                            "    <trialId>"+Math.toIntExact(trialSession.getTrial().getId())+"</trialId>\n" +
                            "    <sessionId>"+Math.toIntExact(trialSession.getId())+"</sessionId>\n" +
                            "    <answerId>"+Math.toIntExact(answer.getId())+"</answerId>\n" +
                            "    <timeSendUTC>"+answer.getSentSimulationTime().atZone(ZoneId.systemDefault()).toEpochSecond()+"</timeSendUTC>\n" +
                            "    <timeWhen>"+answer.getSimulationTime().toInstant().toEpochMilli()+"</timeWhen>\n" +
                            "    <observationTypeName>"+observationType.getName()+"</observationTypeName>\n" +
                            "    <observervationTypeId>"+Math.toIntExact(observationType.getId())+"</observervationTypeId>\n" +
                            "    <observationTypeDescription>"+observationType.getDescription()+"</observationTypeDescription>\n" +
                            "    <description>"+ attachmentDescription  +"</description>\n" +
                            "    <multiplicity>"+observationType.isMultiplicity()+"</multiplicity>\n" +
                            "    <questions>\n" +questionsXml + "</questions>\n" +
                            "</alert>";


            String topic = "system_observer_tool_answer";
            CISAdapter adapter=adapterInit();
            adapter.createProducer(topic);
            try {
                adapter.sendMessage(generateAvroFromXML(xmlInString), topic);
            } catch (CommunicationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private static GenericRecord generateAvroFromXML(String XML){
        DatumBuilder datumBuilder = new DatumBuilder(ObserverToolAnswer.getClassSchema());

        return datumBuilder.createDatum(XML);
    }

}
