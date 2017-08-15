package com.example.jms.service.article.jms;

import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.example.jms.service.article.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ArticleListener {

    private final ObjectMapper mapper;
    private final JmsTemplate jmsTemplate;
    private final ArticleService articleService;

    public ArticleListener(ArticleService articleService, ObjectMapper objectMapper, JmsTemplate jmsTemplate) {
        this.articleService = articleService;
        this.mapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "queue.article", containerFactory = "containerFactory", selector = "operation = 'create'")
    public void createArticle(Message<String> message) throws IOException {
        String received = message.getPayload();
        log.info("Received article for create: {}", received);
        ArticleMessage articleMessage = mapper.readValue(received, ArticleMessage.class);
        articleService.saveArticle(articleMessage);
    }

    @JmsListener(destination = "queue.article", containerFactory = "containerFactory", selector = "operation = 'update'")
    public void updateArticle(Message<String> message) throws IOException {
        String received = message.getPayload();
        log.info("Received article for update: {}", received);
        ArticleMessage articleMessage = mapper.readValue(received, ArticleMessage.class);
        articleService.saveArticle(articleMessage);
    }

    @JmsListener(destination = "queue.article", containerFactory = "containerFactory", selector = "operation = 'read'")
    public void readArticles(Message<String> message) throws IOException {
        log.info("Getting articles");
        log.debug("Message Headers: {}", message.getHeaders());
        log.debug("Message Payload: {}", message.getPayload());
        List<ArticleMessage> articles = articleService.getArticles();
        String articleJson = mapper.writeValueAsString(articles);
        // It seems I shouldn't have to strip the beginning of the queue name
        jmsTemplate.convertAndSend(message.getHeaders().get("jms_replyTo").toString().substring(13), articleJson);
    }
}
