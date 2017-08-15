package com.example.jms.service.article.service;

import com.example.jms.service.article.jms.messagetypes.ArticleMessage;
import com.example.jms.service.article.model.Article;

import java.util.List;

public interface ArticleService {

    Article saveArticle(ArticleMessage articleMessage);

    List<ArticleMessage> getArticles();
}
