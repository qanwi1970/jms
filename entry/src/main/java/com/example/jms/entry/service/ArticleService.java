package com.example.jms.entry.service;

import com.example.jms.entry.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles();

    void addArticle(Article article);
}
