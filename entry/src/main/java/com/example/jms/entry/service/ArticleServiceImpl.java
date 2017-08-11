package com.example.jms.entry.service;

import com.example.jms.entry.jms.ArticleSender;
import com.example.jms.entry.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        List<Article> list = new ArrayList<>();
        list.add(new Article(1, "Building a Binary Clock", "IoT", "body text"));
        list.add(new Article(2, "Systematically Tackling Performance Issues", "Programming", "body text"));
        list.add(new Article(3, "Allomancy in D20: Soothing", "Gaming", "body text"));
        return list;
    }

    @Override
    public void addArticle(Article article) {
        articleSender.sendArticleForAdd(article);
    }
}
