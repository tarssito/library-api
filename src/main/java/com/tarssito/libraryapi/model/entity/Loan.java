package com.tarssito.libraryapi.model.entity;

import com.tarssito.libraryapi.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    private Long id;
    private String costumer;
    private Book book;
    private LocalDate loanDate;
    private Boolean returned;
}
