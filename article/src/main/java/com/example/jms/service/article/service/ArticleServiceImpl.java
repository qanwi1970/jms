package com.example.jms.service.article.service;

import com.example.jms.service.article.jms.messagetypes.Article;
import com.example.jms.service.article.jms.messagetypes.CrudWrapper;
import com.example.jms.service.article.repositories.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void processArticleMessage(CrudWrapper<Article> articleMessage) {
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
    public com.example.jms.service.article.model.Article addArticle(Article article) {
        com.example.jms.service.article.model.Article articleEntity =
                com.example.jms.service.article.model.Article.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .category(article.getCategory())
                        .body(article.getBody())
                        .published(article.isPublished())
                        .build();
        com.example.jms.service.article.model.Article savedArticle = articleRepository.save(articleEntity);
        log.debug(savedArticle.toString());
        return savedArticle;
    }
}
