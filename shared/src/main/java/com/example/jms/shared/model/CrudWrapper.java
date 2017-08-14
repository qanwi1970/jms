package com.example.jms.shared.model;

import lombok.Data;

import java.io.Serializable;

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
