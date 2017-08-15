package com.example.jms.entry.service;

import com.example.jms.entry.jms.ArticleSender;
import com.example.jms.entry.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleSender articleSender;

    public ArticleServiceImpl(ArticleSender articleSender) {
        this.articleSender = articleSender;
    }

    @Override
    public List<Article> getAllArticles() {
        try {
            return articleSender.getArticles();
        } catch (JMSException e) {
            log.error("Error receiving message", e);
            return new ArrayList<>();
        } catch (IOException e) {
            log.error("Error parsing message body", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void addArticle(Article article) {
        articleSender.sendArticleForAdd(article);
    }
}
