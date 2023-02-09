package eu.fp7.driver.ost.driver.util;

import eu.fp7.driver.ost.driver.dto.AnswerKafkaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//import javax.annotation.PostConstruct;

@Service
public class KafkaUtil {
    public static String answerTopic = "ostAnswer";
    @Autowired
    private KafkaTemplate<String, AnswerKafkaDTO> kafkaTemplate;
    @Value("${driver.is_testbed_on}")
    private boolean is_testbed_on;

//    @PostConstruct
//    private void init(){
//        if (!is_testbed_on) return;
//        createNewTopic(answerTopic);
//    }

//    public void createNewTopic(String topicName){
//        TopicBuilder.name(topicName)
//                .partitions(10)
//                .replicas(1)
//                .build();
//    }

    public void sendAnswer(AnswerKafkaDTO answer){
        if (!is_testbed_on) return;
        kafkaTemplate.send(answerTopic, answer);
    }
}
