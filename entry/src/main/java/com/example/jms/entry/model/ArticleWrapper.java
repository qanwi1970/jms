package com.example.jms.entry.model;

import lombok.Data;

@Data
public class ArticleWrapper extends MessageWrapper {

    private Article article;

}
