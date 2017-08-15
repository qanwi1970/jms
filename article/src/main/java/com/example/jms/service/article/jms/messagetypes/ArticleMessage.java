package com.example.jms.service.article.jms.messagetypes;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * What's important about this POJO is that it is different from the POJO that was used in sending the message. I added
 * the published field to this service only to demonstrate how loosely coupled services can actually be.
 */
@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleMessage {

    private long articleId;

    private String title;

    private String category;

    private String body;

    private boolean published;

}
