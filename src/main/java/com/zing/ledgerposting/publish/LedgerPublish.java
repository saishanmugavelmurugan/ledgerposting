package com.zing.ledgerposting.publish;

import com.google.gson.Gson;
import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class LedgerPublish {
    @Value("${lp.topic.publish}")
    private String topicName;
    Logger logger = LoggerFactory.getLogger(LedgerPublish.class);
    private Gson gson;
    private KafkaTemplate<String, String> kafkaTemplate;

    public LedgerPublish(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    /**
     * Publish message
     * @param ledgerEntryEntity
     * @return boolean
     */
    public void publishMessage(final LedgerEntryEntity ledgerEntryEntity) {
        try{
            CompletableFuture<SendResult<String,String>> completableFuture= kafkaTemplate.send(topicName, gson.toJson(ledgerEntryEntity));
            completableFuture.whenComplete((sendResult, throwable) -> {
                if (throwable != null) {
                    logger.error("Error Sending the Message and the exception is {}", throwable.getMessage());
                } else {
                    logger.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", ledgerEntryEntity.getId().toString(), gson.toJson(ledgerEntryEntity), sendResult.getRecordMetadata().partition());
                }
            });
        } catch (Exception e) {
            logger.error("Exception Sending the Message and the exception is {}", e.getMessage());
        }

    }
}
