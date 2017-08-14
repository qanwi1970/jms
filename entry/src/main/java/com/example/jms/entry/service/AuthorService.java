package com.example.jms.entry.service;

import com.example.jms.entry.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    void addAuthor(Author author);

}
