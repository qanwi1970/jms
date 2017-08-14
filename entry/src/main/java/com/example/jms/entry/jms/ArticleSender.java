package com.example.jms.entry.jms;

import com.example.jms.entry.model.Article;
import com.example.jms.entry.model.CrudWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

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
        CrudWrapper<Article> articleWrapper = new CrudWrapper<>();
        articleWrapper.setAction(CrudWrapper.Action.Create);
        articleWrapper.setPayload(article);
        String message;
        try {
            message = mapper.writeValueAsString(articleWrapper);
        } catch (JsonProcessingException e) {
            log.error("Could not convert Article to Json", e);
            return;
        }
        jmsTemplate.convertAndSend("article.queue", message);
    }
}
