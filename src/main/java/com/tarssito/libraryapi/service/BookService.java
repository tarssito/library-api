package com.tarssito.libraryapi.service;

import com.tarssito.libraryapi.model.entity.Book;

import java.util.Optional;

public interface BookService {
    Book save(Book book);

    Optional<Book> getByID(Long id);

    void delete(Book book);

    Book update(Book book);
}
