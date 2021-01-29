package com.tarssito.libraryapi.service;

import com.tarssito.libraryapi.exception.BusinessException;
import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.entity.Loan;
import com.tarssito.libraryapi.model.repository.LoanRepository;
import com.tarssito.libraryapi.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    @MockBean
    LoanRepository repository;

    LoanService service;

    @BeforeEach
    public void setUp() {
        service = new LoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um empréstimo")
    public void saveLoanTest() {
        String costumer = "Fulano";

        Book book = Book.builder()
                .id(1L)
                .build();

        Loan loanSaving = Loan.builder()
                .costumer(costumer)
                .book(book)
                .loanDate(LocalDate.now())
                .build();

        Loan loanSaved = Loan.builder()
                .id(1L)
                .costumer(costumer)
                .book(book)
                .loanDate(LocalDate.now())
                .build();

        when(repository.existsByBookNotReturned(book)).thenReturn(false);
        when(repository.save(loanSaving)).thenReturn(loanSaved);

        Loan loan = service.save(loanSaving);

        assertThat(loan.getId()).isEqualTo(loanSaved.getId());
        assertThat(loan.getBook().getId()).isEqualTo(loanSaved.getBook().getId());
        assertThat(loan.getCostumer()).isEqualTo(loanSaved.getCostumer());
        assertThat(loan.getLoanDate()).isEqualTo(loanSaved.getLoanDate());
    }

    @Test
    @DisplayName("Deve lançar um erro de negócio ao tentar salvar um empréstimo de um livro já emprestado")
    public void loanedBookSaveTest() {
        Book book = Book.builder()
                .id(1L)
                .build();

        Loan loan = Loan.builder()
                .costumer("Fulano")
                .book(book)
                .loanDate(LocalDate.now())
                .build();

        when(repository.existsByBookNotReturned(book)).thenReturn(true);

        Throwable exception = catchThrowable(() -> service.save(loan));

        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Book already loaned");

        verify(repository, never()).save(loan);
    }
}
