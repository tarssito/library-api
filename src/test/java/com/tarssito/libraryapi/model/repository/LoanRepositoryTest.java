package com.tarssito.libraryapi.model.repository;

import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.entity.Loan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.tarssito.libraryapi.model.repository.BookRepositoryTest.createNewBook;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoanRepository repository;


    @Test
    @DisplayName("Deve verificar se não existe empréstimo não devolvido para o livro")
    public void existsByBookNotReturnedTest() {
        Book book = createNewBook("123");
        entityManager.persist(book);

        Loan loan = Loan.builder()
                .book(book)
                .costumer("Fulano")
                .loanDate(LocalDate.now())
                .build();
        entityManager.persist(loan);

        boolean exists = repository.existsByBookNotReturned(book);

        assertThat(exists).isTrue();
    }
}
