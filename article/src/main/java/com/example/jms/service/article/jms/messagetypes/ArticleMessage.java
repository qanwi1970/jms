package com.example.jms.service.article.jms.messagetypes;

import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleMessage {

    private int articleId;

    private String title;

    private String category;

    private String body;

    private boolean published;

}
