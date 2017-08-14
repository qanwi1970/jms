package com.example.jms.entry.controller;

import com.example.jms.entry.model.Article;
import com.example.jms.entry.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public List<Article> getAllArticles() {
        log.debug("Getting all articles");
        return articleService.getAllArticles();
    }

    /**
     * In an async workflow, the entity will _eventually_ get created. We don't have a location for it, or a proper
     * model with an ID. So, we'll just return a 202 as soon as the service call to put the addition on the queue
     * succeeds.
     *
     * @param article The article to add
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addArticle(@RequestBody Article article) {
        log.debug("Adding an article {}", article);
        articleService.addArticle(article);
    }
}
