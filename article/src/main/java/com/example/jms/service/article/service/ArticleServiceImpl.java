package com.example.jms.service.article.service;

import com.example.jms.service.article.jms.ArticleAnnouncer;
import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.example.jms.service.article.jms.messagetypes.CrudWrapper;
import com.example.jms.service.article.model.Article;
import com.example.jms.service.article.repositories.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void processArticleMessage(CrudWrapper<ArticleMessage> articleMessage) {
        switch (articleMessage.getAction()) {
            case Create:
                addArticle(articleMessage.getPayload());
                break;
            case Read:
                break;
            case Update:
                break;
            case Delete:
                break;
            default:
                break;
        }
    }

    @Override
    public Article addArticle(ArticleMessage articleMessage) {
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
}
