package com.tarssito.libraryapi.api.controller;

import com.tarssito.libraryapi.api.dto.BookDTO;
import com.tarssito.libraryapi.api.dto.LoanDTO;
import com.tarssito.libraryapi.api.dto.LoanFilterDTO;
import com.tarssito.libraryapi.api.dto.ReturnedLoanDTO;
import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.entity.Loan;
import com.tarssito.libraryapi.service.BookService;
import com.tarssito.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto) {
        Book book = bookService.getBookByIsbn(dto.getIsbn()).
                orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn"));
        Loan entity = Loan.builder()
                    .book(book)
                    .costumer(dto.getCostumer())
                    .loanDate(LocalDate.now())
                    .build();
        Loan loan = loanService.save(entity);
        return loan.getId();
    }

    @PatchMapping("{id}")
    public void returnBook(@PathVariable Long id, @RequestBody ReturnedLoanDTO dto) {
        Loan loan = loanService.getById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        loan.setReturned(dto.getReturned());
        loanService.update(loan);
    }

    @GetMapping
    public Page<LoanDTO> find(LoanFilterDTO dto, Pageable pageRequest) {
        Page<Loan> page = loanService.find(dto, pageRequest);
        List<LoanDTO> loans = page.getContent()
                .stream()
                .map(entity -> {
                    Book book = entity.getBook();
                    BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
                    LoanDTO loanDTO = modelMapper.map(entity, LoanDTO.class);
                    loanDTO.setBook(bookDTO);
                    return loanDTO;
                }).collect(Collectors.toList());
        return new PageImpl<LoanDTO>(loans, pageRequest, page.getTotalElements());
    }
}
