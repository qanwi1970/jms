package com.example.jms.service.article.jms;

import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleAnnouncer {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;

    public ArticleAnnouncer(JmsTemplate jmsTemplate, ObjectMapper mapper) {
        this.jmsTemplate = jmsTemplate;
        this.mapper = mapper;
    }

    public void announceArticle(ArticleMessage articleMessage) {
        String message;
        try {
            message = mapper.writeValueAsString(articleMessage);
        } catch (JsonProcessingException e) {
            log.error("Could not announce new article", e);
            return;
        }
        jmsTemplate.convertAndSend(new ActiveMQTopic("article.announce"), message);
        log.info("New article announced");
    }
}
