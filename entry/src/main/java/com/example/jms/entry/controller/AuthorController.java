package com.example.jms.entry.controller;

import com.example.jms.entry.model.Author;
import com.example.jms.entry.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAuthors() {
        log.debug("Getting all authors");
        return authorService.getAllAuthors();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addAuthor(@RequestBody Author author) {
        log.debug("Adding an author");
        authorService.addAuthor(author);
    }
}
