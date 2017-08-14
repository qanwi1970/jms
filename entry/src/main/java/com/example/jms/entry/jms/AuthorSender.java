package com.example.jms.entry.jms;

import com.example.jms.entry.model.Author;
import com.example.jms.shared.model.AuthorMessage;
import com.example.jms.shared.model.CrudWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorSender {

    private final JmsTemplate jmsTemplate;

    public AuthorSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendAuthorForAdd(Author author) {
        log.debug("Sending a new author to be added: {}", author);
        AuthorMessage authorMessage = new AuthorMessage(author.getAuthorId(), author.getFirstName(),
                author.getLastName());
        CrudWrapper<AuthorMessage> authorWrapper = new CrudWrapper<>();
        authorWrapper.setAction(CrudWrapper.Action.Create);
        authorWrapper.setPayload(authorMessage);
        log.debug("AuthorWrapper: {}", authorWrapper);
        jmsTemplate.send("queue.author", session -> session.createObjectMessage(authorWrapper));
    }
}
