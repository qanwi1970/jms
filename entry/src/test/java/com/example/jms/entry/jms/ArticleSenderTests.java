package com.example.jms.entry.jms;

import com.example.jms.entry.model.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleSenderTests {

    @MockBean
    private JmsTemplate mockJmsTemplate;

    @MockBean
    private ObjectMapper mockObjectMapper;

    @Autowired
    private ArticleSender articleSender;

    @Test
    public void articleSenderLoads() {
        // Arrange
        // Act
        // Assert
        Assert.assertNotNull(articleSender);
    }

    @Test
    public void sendArticleForAdd_convertsArticle() throws JsonProcessingException {
        // Arrange
        Article article = new Article();
        String articleJson = "{\"articleId\": 1}";
        Mockito.when(mockObjectMapper.writeValueAsString(article)).thenReturn(articleJson);

        // Act
        articleSender.sendArticleForAdd(article);

        // Assert
        Mockito.verify(mockObjectMapper).writeValueAsString(article);
    }
}
