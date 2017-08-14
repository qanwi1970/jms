package com.example.jms.entry.service;

import com.example.jms.entry.jms.AuthorSender;
import com.example.jms.entry.model.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorSender authorSender;

    public AuthorServiceImpl(AuthorSender authorSender) {
        this.authorSender = authorSender;
    }

    @Override
    public List<Author> getAllAuthors(){
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "Doctor", "Seuss"));
        authors.add(new Author(2, "R. A.", "Salvatore"));
        authors.add(new Author(3, "Brandon", "Sanderson"));
        return authors;
    }

    @Override
    public void addAuthor(Author author) {
        authorSender.sendAuthorForAdd(author);
    }
}
