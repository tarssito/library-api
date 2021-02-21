package com.tarssito.libraryapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String costumer;

    @Column(name = "costumer_email")
    private String costumerEmail;

    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

    private LocalDate loanDate;
    private Boolean returned;
}
