package com.example.jms.entry.controller;

import com.example.jms.entry.jms.ArticleSender;
import com.example.jms.entry.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.jemos.podam.api.PodamFactory;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("api/performance")
public class PerformanceController {

    private final PodamFactory podamFactory;
    private final ArticleSender articleSender;
    private Article[] articles = new Article[100];

    public PerformanceController(PodamFactory podamFactory, ArticleSender articleSender) {
        this.podamFactory = podamFactory;
        this.articleSender = articleSender;
    }

    @PostConstruct
    public void init() {
        log.debug("Building Article array");
        for (int i = 0; i < 100; i++) {
            articles[i] = podamFactory.manufacturePojo(Article.class);
        }
    }

    @PostMapping("batch")
    public String sendBatch(@RequestParam int size) throws JMSException {
        log.debug("Sending {} messages", size);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, 100);
            articleSender.sendArticle(articles[index]);
        }
        long diff = System.currentTimeMillis() - startTime;
        return "Sent " + size + " messages in " + diff + " milliseconds";
    }

}
