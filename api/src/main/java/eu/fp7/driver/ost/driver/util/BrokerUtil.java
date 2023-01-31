//package eu.fp7.driver.ost.driver.util;
//
////import eu.driver.adapter.constants.TopicConstants;
//import eu.driver.adapter.core.CISAdapter;
//import eu.driver.adapter.core.producer.GenericProducer;
////import eu.driver.api.IAdaptorCallback;
//import eu.driver.model.core.AdminHeartbeat;
//import eu.driver.model.core.Heartbeat;
////import eu.driver.model.core.ObserverToolAnswer;
//import eu.driver.model.core.RequestChangeOfTrialStage;
//import eu.driver.model.core.Timing;
////import eu.fp7.driver.ost.driver.model.Answer;
////import eu.fp7.driver.ost.driver.model.Attachment;
////import eu.fp7.driver.ost.driver.model.ObservationType;
////import eu.fp7.driver.ost.driver.model.Trial;
////import eu.fp7.driver.ost.driver.model.TrialSession;
////import eu.fp7.driver.ost.driver.model.TrialStage;
////import eu.fp7.driver.ost.driver.model.enums.AnswerType;
////import eu.fp7.driver.ost.driver.model.enums.SessionStatus;
//import eu.fp7.driver.ost.driver.repository.TrialSessionRepository;
//import eu.fp7.driver.ost.driver.repository.TrialStageRepository;
////import eu.fp7.driver.ost.driver.util.schema.SchemaCreator;
////import org.apache.avro.generic.GenericData;
////import org.apache.avro.generic.GenericRecord;
////import org.apache.avro.generic.GenericRecordBuilder;
////import org.apache.avro.generic.IndexedRecord;
////import org.apache.avro.specific.SpecificData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
////import java.time.Instant;
////import java.time.LocalDateTime;
////import java.time.LocalTime;
////import java.time.ZoneId;
////import java.time.ZonedDateTime;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Objects;
////import java.util.Optional;
////import org.springframework.kafka.config.TopicBuilder;
//
//
//@Service
//public class BrokerUtil {
//
////    private enum testBedTopicEnum {system_observer_tool_answer}
////
////    static {
////        CISAdapter.globalConfigPath = "/opt/config";
////    }
//
////    private static RequestChangeOfTrialStage requestChangeOfTrialStage = null;
////    static TrialSessionRepository trialSessionRepositoryStatic;
////    static TrialStageRepository trialStageRepositoryStatic;
////    public static long trialId;
////    public static long trialSessionId;
////    public static long trialStageId;
////    static Heartbeat heartbeat;
////    static AdminHeartbeat adminHeartbeat;
////    public static Timing timing;
//
//
////    @Autowired
////    TrialSessionRepository trialSessionRepository;
////
////    @Autowired
////    TrialStageRepository trialStageRepository;
////
////    @Value("${driver.is_testbed_on}")
////    private boolean is_testbed_on;
////
////    CISAdapter adapter;
////    GenericProducer answerProducer;
//
//
//    @PostConstruct
//    public void init() {
//
////        if (!is_testbed_on) return;
//        //trialSessionRepositoryStatic = trialSessionRepository;
//        //trialStageRepositoryStatic = trialStageRepository;
//        //this.adapter = CISAdapter.getNewInstance();
//        //this.answerProducer = adapter.createProducer(testBedTopicEnum.system_observer_tool_answer.name());
//
////        adapter.addCallback(new CallbackValue_TRIAL_STATE_CHANGE_TOPIC(), TopicConstants.TRIAL_STATE_CHANGE_TOPIC);
////        adapter.addCallback(new CallbackValue_HEARTBEAT_TOPIC(), TopicConstants.HEARTBEAT_TOPIC);
////        adapter.addCallback(new CallbackValue_TIMING_TOPIC(), TopicConstants.TIMING_TOPIC);
////        adapter.addCallback(new CallbackValue_ADMIN_HEARTBEAT_TOPIC(), TopicConstants.ADMIN_HEARTBEAT_TOPIC);
////        test();1,
//
//    }
//
////    public void test() {
////        sendAnswerToTestBed(getTestAnswer());
////        trialId = 9;
////        trialSessionId = 199;
////        trialStageId = 51;
////        setLastTrialStage();
////    }
////
////    public static LocalDateTime getTrialTime() {
////
////        if (timing != null) {
////            return Instant.ofEpochMilli(timing.getTrialTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
////        }
////        return Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
////    }
////
////    public static LocalTime getTimeElapsed() {
////
////        if (timing != null) {
////            return Instant.ofEpochMilli(timing.getTimeElapsed()).atZone(ZoneId.systemDefault()).toLocalTime();
////        }
////        return LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toLocalTime();
////    }
////
////
////    public void sendAnswer(GenericRecord formattedAnswer) {
////        if (!is_testbed_on) return;
////        answerProducer.send(formattedAnswer);
////    }
////
////    public static void setLastTrialStage() {
////        System.out.println("trialId= " + trialId + "   trialSessionId= " + trialSessionId + "   trialStageId= " + trialStageId);
////        Optional<TrialSession> trialSession = trialSessionRepositoryStatic.findByIdAndStatus(trialSessionId, SessionStatus.ACTIVE);
////        if (trialSession.isPresent()) {
////            Trial trial = trialSession.get().getTrial();
////            Optional<TrialStage> trialStage = trialStageRepositoryStatic.findByIdAndTrialId(trialStageId, trial.getId());
////            if (trialStage.isPresent()) {
////                trialSession.get().setLastTrialStage(trialStage.get());
////                trialSessionRepositoryStatic.save(trialSession.get());
////                System.out.println("Trial Stage changed to " + trialStage.get().getId());
////            } else {
////                System.out.println("Trial Stage does not exist");
////            }
////        } else {
////            System.out.println("Trial Status with ACTIVE status does not exist");
////        }
////    }
////
////    private Answer getTestAnswer() {
////        Answer answer = new Answer();
////        answer.id = 0l;
////        answer.setFieldValue("test fieldValue");
////        answer.setComment("test comment");
////        answer.setDeleteComment("test deleteComment");
////        answer.setTrialTime(LocalDateTime.now());
////        answer.setFormData("");
////        answer.setAttachments(new ArrayList<>());
////        answer.setSentSimulationTime(LocalDateTime.now());
////        answer.setSimulationTime(ZonedDateTime.now());
////        TrialSession trialSession = new TrialSession();
////        trialSession.setId(0l);
////        Trial trial = new Trial();
////        trial.setId(0l);
////        trialSession.setTrial(trial);
////        answer.setTrialSession(trialSession);
////        ObservationType observationType = new ObservationType();
////        observationType.setId(0l);
////        observationType.setName("test obserwationType");
////        observationType.setDescription("test obserwationType description");
////        observationType.setMultiplicity(false);
////        eu.fp7.driver.ost.driver.model.Question question = new eu.fp7.driver.ost.driver.model.Question();
////        question.setAnswerType(AnswerType.TEXT_FIELD);
////        question.setId(0l);
////        question.setName("test question");
////        question.setDescription("test question description");
////        List<eu.fp7.driver.ost.driver.model.Question> questionList = new ArrayList<>();
////        questionList.add(question);
////        observationType.setQuestions(questionList);
////        answer.setObservationType(observationType);
////        return answer;
////    }
////
////    public static class CallbackValue_TIMING_TOPIC implements IAdaptorCallback {
////        public CallbackValue_TIMING_TOPIC() {
////        }
////
////        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
////            try {
////                timing = (eu.driver.model.core.Timing) SpecificData.get().deepCopy(eu.driver.model.core.Timing.SCHEMA$, receivedMessage);
////            } catch (Exception e) {
////                System.out.println("Error heartbeat receive message! " + e.getMessage());
////            }
////            System.out.println("timing receive message! " + timing.getTrialTime());
////        }
////    }
////
////
////    public static class CallbackValue_ADMIN_HEARTBEAT_TOPIC implements IAdaptorCallback {
////        public CallbackValue_ADMIN_HEARTBEAT_TOPIC() {
////        }
////
////        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
////            try {
////                adminHeartbeat = (eu.driver.model.core.AdminHeartbeat) SpecificData.get().deepCopy(eu.driver.model.core.AdminHeartbeat.SCHEMA$, receivedMessage);
////            } catch (Exception e) {
////                System.out.println("Error adminHeartbeat receive message! " + e.getMessage());
////            }
////            System.out.println("adminHeartbeat receive message! " + adminHeartbeat.getId());
////        }
////    }
////
////
////    public static class CallbackValue_HEARTBEAT_TOPIC implements IAdaptorCallback {
////        public CallbackValue_HEARTBEAT_TOPIC() {
////        }
////
////        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
////            try {
////                heartbeat = (eu.driver.model.core.Heartbeat) SpecificData.get().deepCopy(eu.driver.model.core.Heartbeat.SCHEMA$, receivedMessage);
////            } catch (Exception e) {
////                System.out.println("Error heartbeat receive message! " + e.getMessage());
////            }
////            System.out.println("heartbeat receive message! " + heartbeat.getId());
////        }
////    }
////
////
////    public static class CallbackValue_TRIAL_STATE_CHANGE_TOPIC implements IAdaptorCallback {
////        public CallbackValue_TRIAL_STATE_CHANGE_TOPIC() {
////        }
////
////        public void messageReceived(IndexedRecord key, IndexedRecord receivedMessage, String topicName) {
////            System.out.println("receive change stage message");
////            if (receivedMessage.getSchema().getName().equalsIgnoreCase("RequestChangeOfTrialStage")) {
////                try {
////                    requestChangeOfTrialStage = (eu.driver.model.core.RequestChangeOfTrialStage) SpecificData.get().deepCopy(eu.driver.model.core.RequestChangeOfTrialStage.SCHEMA$, receivedMessage);
////                } catch (Exception e) {
////                    System.out.println("Error RequestChangeOfTrialStage receive message! " + e.getMessage());
////                }
////                System.out.println("change stage message");
////                if (requestChangeOfTrialStage != null) {
////                    System.out.println("change stage message: " + requestChangeOfTrialStage.toString());
////                    trialId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialId()).orElse(0);
////                    trialSessionId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialSessionId()).orElse(0);
////                    trialStageId = Optional.ofNullable(requestChangeOfTrialStage.getOstTrialStageId()).orElse(0);
////                    setLastTrialStage();
////                }
////            }
////        }
////    }
////
////    public void sendAnswerToTestBed(Answer answer) {
////        if (!is_testbed_on) return;
////        List<GenericRecord> questionArray = new ArrayList<>();
////
////        for (eu.fp7.driver.ost.driver.model.Question question : answer.getObservationType().getQuestions()) {
////
////            AnswerType questionEnumTypeOfAnswer = question.getAnswerType();
////            String questionTypeOfAnswer = "";
////            switch (questionEnumTypeOfAnswer) {
////                case CHECKBOX:
////                    questionTypeOfAnswer = "checkbox";
////                    break;
////                case RADIO_BUTTON:
////                    questionTypeOfAnswer = "radiobutton";
////                    break;
////                case SLIDER:
////                    questionTypeOfAnswer = "slider";
////                    break;
////                case TEXT_FIELD:
////                    questionTypeOfAnswer = "text";
////                    break;
////            }
////
////            GenericRecord formattedQuestion;
////            GenericRecordBuilder questionRecordBuilder = new GenericRecordBuilder(eu.driver.model.core.Question.getClassSchema());
////
////            formattedQuestion = questionRecordBuilder
////                    .set("id", Math.toIntExact(question.getId()))
////                    .set("name", question.getName())
////                    .set("description", question.getDescription())
////                    .set("answer", SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId()))
////                    .set("comment", SchemaCreator.getValueFromJSONObject(answer.getFormData(), AnswerProperties.QUESTION_KEY + question.getId() + AnswerProperties.COMMENT_KEY))
////                    .set("typeOfQuestion", new GenericData.EnumSymbol(eu.driver.model.core.Question.getClassSchema().getField("typeOfQuestion").schema(), questionTypeOfAnswer))
////                    .build();
////
////            questionArray.add(formattedQuestion);
////        }
////
////        String attachmentDescription = "";
////
////        Optional<Attachment> attachment = answer.getAttachments().stream().filter(Objects::nonNull).findFirst();
////
////        if (!attachment.isPresent()) attachmentDescription = "";
////        else attachmentDescription = attachment.get().getDescription();
////
////        GenericRecord formattedAnswer;
////        GenericRecordBuilder recordBuilder = new GenericRecordBuilder(ObserverToolAnswer.getClassSchema());
////        formattedAnswer = recordBuilder
////                .set("trialId", Math.toIntExact(answer.getTrialSession().getTrial().getId()))
////                .set("sessionId", Math.toIntExact(answer.getTrialSession().getId()))
////                .set("answerId", Math.toIntExact(answer.getId()))
////                .set("timeSendUTC", answer.getSentSimulationTime().atZone(ZoneId.systemDefault()).toEpochSecond())
////                .set("timeWhen", answer.getSimulationTime().toInstant().toEpochMilli())
////                .set("observationTypeName", answer.getObservationType().getName())
////                .set("observervationTypeId", Math.toIntExact(answer.getObservationType().getId()))
////                .set("observationTypeDescription", answer.getObservationType().getDescription())
////                .set("description", attachmentDescription)
////                .set("multiplicity", answer.getObservationType().isMultiplicity())
////                .set("questions", questionArray)
////                .build();
////
////        sendAnswer(formattedAnswer);
////    }
//
//}
