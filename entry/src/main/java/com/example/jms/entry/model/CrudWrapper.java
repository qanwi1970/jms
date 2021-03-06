package com.example.jms.entry.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class CrudWrapper<T> implements Serializable {

    public enum Action {
        Create,
        Read,
        Update,
        Delete
    }

    private Action action;

    private T payload;

}
