package id.achfajar.challenge8.kafka;

import id.achfajar.challenge8.dto.producer.EmailProducerDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private NewTopic topic;
    private KafkaTemplate<String, EmailProducerDTO> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailProducer.class);

    @Autowired
    public EmailProducer(NewTopic topic, KafkaTemplate<String, EmailProducerDTO> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(EmailProducerDTO event){
        LOGGER.info(String.format("Email event => %s", event.toString()));
        Message<EmailProducerDTO> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
