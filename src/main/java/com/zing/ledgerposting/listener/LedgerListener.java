package com.zing.ledgerposting.listener;

import com.google.gson.Gson;
import com.zing.ledgerposting.model.Transaction;
import com.zing.ledgerposting.publish.LedgerPublish;
import com.zing.ledgerposting.service.LedgerPostingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class LedgerListener {
    Logger logger = LoggerFactory.getLogger(LedgerPublish.class);

    @Value("${lp.topic.consume}")
    private String topicName;

    private Gson gson;
    private LedgerPostingService ledgerPostingService;

    public LedgerListener(Gson gson,LedgerPostingService ledgerPostingService){
        this.gson = gson;
        this.ledgerPostingService = ledgerPostingService;
    }
    @KafkaListener(id = "lp-consumer", groupId = "#{'${lp.consume.groupid}'}" ,topics = "#{'${lp.topic.consume}'}",containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload String payLoad) {
        try{
            ledgerPostingService.createLedgerEntry(gson.fromJson(payLoad, Transaction.class));
        }catch(Exception e){
            logger.error("Error in processing the message from listner:"+e.getMessage());
        }

    }
}
