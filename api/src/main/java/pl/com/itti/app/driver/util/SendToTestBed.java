//package pl.com.itti.app.driver.util;
//
//import eu.driver.adapter.core.CISAdapter;
//import eu.driver.adapter.excpetion.CommunicationException;
//import eu.driver.model.core.ObserverToolAnswer;
//import org.apache.avro.generic.GenericData;
//import org.apache.avro.generic.GenericRecord;
//import org.apache.avro.generic.GenericRecordBuilder;
//import pl.com.itti.app.driver.model.*;
//import pl.com.itti.app.driver.model.enums.AnswerType;
//import pl.com.itti.app.driver.util.schema.SchemaCreator;
//
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import static pl.com.itti.app.driver.util.SimulationTime.adapterInit;
//
//public class SendToTestBed {
//
//    public static void sendToTestBed(Answer answer, ObservationType observationType, TrialSession trialSession){
//
//        List<GenericRecord> questionArray = new ArrayList<>();
//
//        for (Question question : answer.getObservationType().getQuestions()) {
//
//            AnswerType questionEnumTypeOfAnswer = question.getAnswerType();
//            String questionTypeOfAnswer = "";
//            switch (questionEnumTypeOfAnswer) {
//                case CHECKBOX:
//                    questionTypeOfAnswer = "checkbox";
//                    break;
//                case RADIO_BUTTON:
//                    questionTypeOfAnswer = "radiobutton";
//                    break;
//                case SLIDER:
//                    questionTypeOfAnswer = "slider";
//                    break;
//                case TEXT_FIELD:
//                    questionTypeOfAnswer = "text";
//                    break;
//            }
//
//            GenericRecord formattedQuestion;
//            GenericRecordBuilder questionRecordBuilder = new GenericRecordBuilder(eu.driver.model.core.Question.getClassSchema());
//
//            formattedQuestion = questionRecordBuilder
//                    .set("id", Math.toIntExact(question.getId()))
//                    .set("name", question.getName())
//                    .set("description", question.getDescription())
//                    .set("answer", SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId()))
//                    .set("comment", SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY))
//                    .set("typeOfQuestion", new GenericData.EnumSymbol(eu.driver.model.core.Question.getClassSchema().getField("typeOfQuestion").schema(), questionTypeOfAnswer))
//                    .build();
//
//            questionArray.add(formattedQuestion);
//        }
//
//        String attachmentDescription = "";
//
//        Optional<Attachment> attachment = answer.getAttachments().stream().filter(Objects::nonNull).findFirst();
//
//        if (!attachment.isPresent()) attachmentDescription = "";
//        else attachmentDescription = attachment.get().getDescription();
//
//        GenericRecord formattedAnswer;
//        GenericRecordBuilder recordBuilder = new GenericRecordBuilder(ObserverToolAnswer.getClassSchema());
//        formattedAnswer = recordBuilder
//                .set("trialId", Math.toIntExact(trialSession.getTrial().getId()))
//                .set("sessionId", Math.toIntExact(trialSession.getId()))
//                .set("answerId", Math.toIntExact(answer.getId()))
//                .set("timeSendUTC", answer.getSentSimulationTime().atZone(ZoneId.systemDefault()).toEpochSecond())
//                .set("timeWhen", answer.getSimulationTime().toInstant().toEpochMilli())
//                .set("observationTypeName", observationType.getName())
//                .set("observervationTypeId", Math.toIntExact(observationType.getId()))
//                .set("observationTypeDescription", observationType.getDescription())
//                .set("description", attachmentDescription)
//                .set("multiplicity", observationType.isMultiplicity())
//                .set("questions", questionArray)
//                .build();
//
//        String topic = "system_observer_tool_answer";
//        CISAdapter adapter=adapterInit();
//
//        adapter.createProducer(topic);
//        try {
//            adapter.sendMessage(formattedAnswer, topic);
//        } catch (CommunicationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//
//    }
//}
//
