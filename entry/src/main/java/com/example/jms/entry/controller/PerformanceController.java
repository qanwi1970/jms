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
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("api/performance")
public class PerformanceController {

    private final PodamFactory podamFactory;
    private final ArticleSender articleSender;
    private Article[] articles = new Article[100];
    private Timer timer = new Timer();

    public PerformanceController(PodamFactory podamFactory, ArticleSender articleSender) {
        this.podamFactory = podamFactory;
        this.articleSender = articleSender;
    }

    @PostConstruct
    public void init() {
        log.info("Building Article array");
        for (int i = 0; i < 100; i++) {
            articles[i] = podamFactory.manufacturePojo(Article.class);
        }
    }

    // localhost:8080/api/performance/batch?size=10000
    @PostMapping("batch")
    public String sendBatch(@RequestParam int size) {
        log.info("Sending {} messages", size);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, 100);
            articleSender.sendArticleForAdd(articles[index]);
        }
        long diff = System.currentTimeMillis() - startTime;
        return "Sent " + size + " messages in " + diff + " milliseconds";
    }

    @PostMapping("continuous")
    public String sendContinuous(@RequestParam String command, @RequestParam(required = false) int interval) {
        if (command.equalsIgnoreCase("start")) {
            if (interval <= 0) throw new IllegalArgumentException("Interval must be a positive, non-zero number");
            log.info("Starting to send messages every {} milliseconds", interval);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int index = ThreadLocalRandom.current().nextInt(0, 100);
                    articleSender.sendArticleForAdd(articles[index]);
                }
            }, interval, interval);
            return "Sending messages every " + interval + " milliseconds until told to stop";
        } else {
            log.info("Stopping timer");
            timer.cancel();
            return "Message sending stopped";
        }
    }

    @PostMapping("replyto")
    public String sendReplyTo(@RequestParam int size) throws IOException, JMSException {
        log.info("Sending {} synchronous requests", size);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            articleSender.getArticles();
        }
        long diff = System.currentTimeMillis() - startTime;
        return "Requested " + size + " messages in " + diff + " milliseconds";
    }
}
