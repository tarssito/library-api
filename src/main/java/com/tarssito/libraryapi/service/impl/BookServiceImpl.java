package com.tarssito.libraryapi.service.impl;

import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.repository.BookRepository;
import com.tarssito.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
