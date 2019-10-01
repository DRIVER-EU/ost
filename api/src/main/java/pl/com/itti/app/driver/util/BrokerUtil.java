package pl.com.itti.app.driver.util;

import eu.driver.adapter.constants.TopicConstants;
import eu.driver.adapter.core.CISAdapter;
import eu.driver.adapter.core.producer.GenericProducer;
import eu.driver.api.IAdaptorCallback;
import eu.driver.model.core.ObserverToolAnswer;
import eu.driver.model.core.RequestChangeOfTrialStage;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.specific.SpecificData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.AnswerType;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BrokerUtil {

    private enum testBedTopicEnum {system_observer_tool_answer}

    static {
        CISAdapter.globalConfigPath = "/opt/config";
    }

    private static RequestChangeOfTrialStage requestChangeOfTrialStage = null;
    static TrialSessionRepository trialSessionRepositoryStatic;
    static TrialStageRepository trialStageRepositoryStatic;
    static long trialId;
    static long trialSessionId;
    static long trialStageId;

    @Autowired
    TrialSessionRepository trialSessionRepository;

    @Autowired
    TrialStageRepository trialStageRepository;

    CISAdapter adapter;
    GenericProducer answerProducer;


    @PostConstruct
    public void init() {
        trialSessionRepositoryStatic = trialSessionRepository;
        trialStageRepositoryStatic = trialStageRepository;
        this.adapter = CISAdapter.getInstance();
        this.answerProducer = adapter.createProducer(testBedTopicEnum.system_observer_tool_answer.name());
        adapter.addCallback(new CallbackValue(), TopicConstants.TRIAL_STATE_CHANGE_TOPIC);
    }


    public void sendAnswer(GenericRecord formattedAnswer) {
        answerProducer.send(formattedAnswer);
    }


    public void sendAnswerToTestBed(Answer answer){

        List<GenericRecord> questionArray = new ArrayList<>();

        for (Question question : answer.getObservationType().getQuestions()) {

            AnswerType questionEnumTypeOfAnswer = question.getAnswerType();
            String questionTypeOfAnswer = "";
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

            GenericRecord formattedQuestion;
            GenericRecordBuilder questionRecordBuilder = new GenericRecordBuilder(eu.driver.model.core.Question.getClassSchema());

            formattedQuestion = questionRecordBuilder
                    .set("id", Math.toIntExact(question.getId()))
                    .set("name", question.getName())
                    .set("description", question.getDescription())
                    .set("answer", SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId()))
                    .set("comment", SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY))
                    .set("typeOfQuestion", new GenericData.EnumSymbol(eu.driver.model.core.Question.getClassSchema().getField("typeOfQuestion").schema(), questionTypeOfAnswer))
                    .build();

            questionArray.add(formattedQuestion);
        }

        String attachmentDescription = "";

        Optional<Attachment> attachment = answer.getAttachments().stream().filter(Objects::nonNull).findFirst();

        if (!attachment.isPresent()) attachmentDescription = "";
        else attachmentDescription = attachment.get().getDescription();

        GenericRecord formattedAnswer;
        GenericRecordBuilder recordBuilder = new GenericRecordBuilder(ObserverToolAnswer.getClassSchema());
        formattedAnswer = recordBuilder
                .set("trialId", Math.toIntExact(answer.getTrialSession().getTrial().getId()))
                .set("sessionId", Math.toIntExact(answer.getTrialSession().getId()))
                .set("answerId", Math.toIntExact(answer.getId()))
                .set("timeSendUTC", answer.getSentSimulationTime().atZone(ZoneId.systemDefault()).toEpochSecond())
                .set("timeWhen", answer.getSimulationTime().toInstant().toEpochMilli())
                .set("observationTypeName", answer.getObservationType().getName())
                .set("observervationTypeId", Math.toIntExact(answer.getObservationType().getId()))
                .set("observationTypeDescription", answer.getObservationType().getDescription())
                .set("description", attachmentDescription)
                .set("multiplicity", answer.getObservationType().isMultiplicity())
                .set("questions", questionArray)
                .build();

        sendAnswer(formattedAnswer);
    }

    public static class CallbackValue implements IAdaptorCallback {
        public CallbackValue() {
        }

        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
            if (receivedMessage.getSchema().getName().equalsIgnoreCase("RequestChangeOfTrialStage")) {
                try {
                    requestChangeOfTrialStage = (eu.driver.model.core.RequestChangeOfTrialStage) SpecificData.get().deepCopy(eu.driver.model.core.RequestChangeOfTrialStage.SCHEMA$, receivedMessage);
                } catch (Exception e) {
                    System.out.println("Error RequestChangeOfTrialStage receive message! " + e.getMessage());
                }
                if (requestChangeOfTrialStage != null) {
                    trialId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialId()).orElse(0);
                    trialSessionId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialSessionId()).orElse(0);
                    trialStageId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialStageId()).orElse(0);

                    Optional<TrialSession> trialSession = trialSessionRepositoryStatic.findByStatus(SessionStatus.ACTIVE);
                    Trial trial;

                    if (trialSession.isPresent()) {
                        trial = trialSession.get().getTrial();
                        Optional<pl.com.itti.app.driver.model.TrialStage> trialStage = trialStageRepositoryStatic.findByTrialIdAndTestBedStageId(trial.getId(), trialStageId);
                        if (trialStage.isPresent()) {
                            trialSession.get().setLastTrialStage(trialStage.get());
                            trialSessionRepositoryStatic.save(trialSession.get());
                        } else {
                            System.out.println("Trial Stage does not exist");
                        }
                    } else {
                        System.out.println("Trial Status with ACTIVE status does not exist");
                    }

                }
            }
        }

    }
}
