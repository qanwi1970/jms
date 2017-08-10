package com.example.jms.entry.jms;

import com.example.jms.entry.model.Article;
import com.example.jms.entry.model.MessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleSender {

    private final JmsTemplate jmsTemplate;

    public ArticleSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendArticle(Article article) {
        log.debug("Sending article to queue");
        jmsTemplate.convertAndSend("article.queue", article);
    }

    public void sendArticleForAdd(Article article) {
        log.debug("Sending a new article to be added");
        MessageWrapper<Article> message = new MessageWrapper<>();
        message.setAction("add");
        message.setPayload(article);
        jmsTemplate.convertAndSend("article.queue", message);
    }
}
