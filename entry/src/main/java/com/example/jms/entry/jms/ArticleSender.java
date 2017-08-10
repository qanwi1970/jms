package com.example.jms.entry.jms;

import com.example.jms.entry.model.Article;
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
}
