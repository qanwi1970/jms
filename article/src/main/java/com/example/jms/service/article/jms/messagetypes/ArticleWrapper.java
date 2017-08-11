package com.example.jms.service.article.jms.messagetypes;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ArticleWrapper extends MessageWrapper {

    private Article article;

}
