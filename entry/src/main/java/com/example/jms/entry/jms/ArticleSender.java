package com.example.jms.entry.jms;

import com.example.jms.entry.model.Article;
import com.example.jms.entry.model.CrudWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ArticleSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;

    public ArticleSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        mapper = objectMapper;
    }

    public void sendArticleForAdd(Article article) {
        log.debug("Sending a new article to be added");
        String message;
        try {
            message = mapper.writeValueAsString(article);
        } catch (JsonProcessingException e) {
            log.error("Could not convert Article to Json", e);
            return;
        }
        jmsTemplate.convertAndSend("queue.article", message, Message -> {
            Message.setStringProperty("operation", "create");
            return Message;
        });
    }

    public List<Article> getArticles() throws JMSException, IOException {
        log.debug("Get articles");
        Message reply = jmsTemplate.sendAndReceive("queue.article", session -> {
            Message message = session.createTextMessage("read");
            message.setStringProperty("operation", "read");
            return message;
        });
        log.debug("Reply: {}", reply);
        return mapper.readValue(((TextMessage)reply).getText(), new TypeReference<List<Article>>(){});
    }
}
