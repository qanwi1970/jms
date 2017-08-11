package com.example.jms.service.article.service;

import com.example.jms.service.article.jms.messagetypes.Article;
import com.example.jms.service.article.jms.messagetypes.CrudWrapper;

public interface ArticleService {

    com.example.jms.service.article.model.Article addArticle(Article article);

    void processArticleMessage(CrudWrapper<Article> articleMessage);
}
