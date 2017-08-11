package com.example.jms.service.article.jms;

import com.example.jms.service.article.jms.messagetypes.ArticleWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ArticleListener {

    private final ObjectMapper mapper;

    public ArticleListener() {
        mapper = new ObjectMapper();
    }

    @JmsListener(destination = "article.queue", containerFactory = "containerFactory")
    public void receiveArticle(String received) throws IOException {
        log.info("Received article: {}", received);
        ArticleWrapper articleMessage = mapper.readValue(received, ArticleWrapper.class);
        log.debug("Deserialized message: {}", articleMessage);
    }
}
