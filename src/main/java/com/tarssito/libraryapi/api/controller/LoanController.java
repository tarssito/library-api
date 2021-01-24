package com.tarssito.libraryapi.api.controller;

import com.tarssito.libraryapi.api.dto.LoanDTO;
import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.entity.Loan;
import com.tarssito.libraryapi.service.BookService;
import com.tarssito.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto) {
        Book book = bookService.getBookByIsbn(dto.getIsbn()).get();
        Loan entity = Loan.builder()
                    .book(book)
                    .costumer(dto.getCostumer())
                    .loanDate(LocalDate.now())
                    .build();
        Loan loan = loanService.save(entity);
        return loan.getId();
    }
}
