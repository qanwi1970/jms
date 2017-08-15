package com.example.jms.service.article.service;

import com.example.jms.service.article.jms.ArticleAnnouncer;
import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.example.jms.service.article.jms.messagetypes.CrudWrapper;
import com.example.jms.service.article.model.Article;
import com.example.jms.service.article.repositories.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleAnnouncer articleAnnouncer;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleAnnouncer articleAnnouncer) {
        this.articleRepository = articleRepository;
        this.articleAnnouncer = articleAnnouncer;
    }

    @Override
    public Article saveArticle(ArticleMessage articleMessage) {
        Article articleEntity = Article.builder()
                .articleId(articleMessage.getArticleId())
                .title(articleMessage.getTitle())
                .category(articleMessage.getCategory())
                .body(articleMessage.getBody())
                .published(articleMessage.isPublished())
                .build();
        Article savedArticle = articleRepository.save(articleEntity);
        log.debug(savedArticle.toString());
        articleAnnouncer.announceArticle(articleMessage);
        return savedArticle;
    }

    @Override
    public List<ArticleMessage> getArticles() {
        return articleRepository.findAll().stream()
                .map(article -> ArticleMessage.builder()
                        .articleId(article.getArticleId())
                        .category(article.getCategory())
                        .title(article.getTitle())
                        .body(article.getBody())
                        .published(article.isPublished())
                        .build())
                .collect(Collectors.toList());
    }
}
