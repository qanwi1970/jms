package com.example.jms.entry.model;

import lombok.Data;

@Data
public class MessageWrapper<T> {

    private String action;

    private T payload;

}
