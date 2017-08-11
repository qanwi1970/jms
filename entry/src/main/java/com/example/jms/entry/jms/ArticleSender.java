package com.example.jms.entry.jms;

import com.example.jms.entry.model.Article;
import com.example.jms.entry.model.ArticleWrapper;
import com.example.jms.entry.model.MessageWrapper;
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

    public ArticleSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        mapper = new ObjectMapper();
    }

    public void sendArticleForAdd(Article article) {
        log.debug("Sending a new article to be added");
        ArticleWrapper articleWrapper = new ArticleWrapper();
        articleWrapper.setAction("add");
        articleWrapper.setArticle(article);
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
