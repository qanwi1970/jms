package com.example.jms.service.article.service;

import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.example.jms.service.article.jms.messagetypes.CrudWrapper;

public interface ArticleService {

    com.example.jms.service.article.model.Article addArticle(ArticleMessage articleMessage);

    void processArticleMessage(CrudWrapper<ArticleMessage> articleMessage);
}
