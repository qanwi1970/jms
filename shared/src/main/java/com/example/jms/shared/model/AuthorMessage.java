package com.example.jms.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorMessage implements Serializable {

    private long authorId;

    private String firstName;

    private String lastName;

}
