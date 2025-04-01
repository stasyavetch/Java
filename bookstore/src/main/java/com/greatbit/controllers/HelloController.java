package com.greatbit.controllers;

import com.greatbit.models.BookStorage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Spring main";
    }

    @RequestMapping("/books")
    public String books() {
        return BookStorage.getBooks()
                .stream()
                .map(book -> format("%s - %s - %s </br>", book.getName(), book.getAuthor(), book.getPages()))
                .collect(Collectors.joining("</br>"));
    }
}
