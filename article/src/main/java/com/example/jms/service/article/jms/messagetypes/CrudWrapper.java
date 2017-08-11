package com.example.jms.service.article.jms.messagetypes;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CrudWrapper<T> {

    public enum Action {
        Create,
        Read,
        Update,
        Delete
    }

    private Action action;

    private T payload;

}
