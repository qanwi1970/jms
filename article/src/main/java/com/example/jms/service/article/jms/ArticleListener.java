package com.example.jms.service.article.jms;

import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.example.jms.service.article.jms.messagetypes.CrudWrapper;
import com.example.jms.service.article.service.ArticleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ArticleListener {

    private final ObjectMapper mapper;
    private final ArticleService articleService;

    public ArticleListener(ArticleService articleService) {
        this.articleService = articleService;
        mapper = new ObjectMapper();
    }

    @JmsListener(destination = "article.queue", containerFactory = "containerFactory")
    public void receiveArticle(String received) throws IOException {
        log.info("Received article: {}", received);
        CrudWrapper<ArticleMessage> articleMessage = mapper.readValue(received, new TypeReference<CrudWrapper<ArticleMessage>>(){});
        articleService.processArticleMessage(articleMessage);
    }
}
