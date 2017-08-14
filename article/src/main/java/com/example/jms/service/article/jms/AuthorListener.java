package com.example.jms.service.article.jms;

import com.example.jms.shared.model.AuthorMessage;
import com.example.jms.shared.model.CrudWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorListener {

    @JmsListener(destination = "queue.author", containerFactory = "containerFactory")
    public void receiveAuthor(Message<CrudWrapper<AuthorMessage>> message) {
        CrudWrapper<AuthorMessage> wrapper = message.getPayload();
        log.info("Received Author: {}", wrapper);
    }
}
