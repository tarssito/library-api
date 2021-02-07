package com.tarssito.libraryapi.model.repository;

import com.tarssito.libraryapi.model.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookRepository repository;


    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
    public void returnTrueWhenIsbnExists() {
        String isbn = "123";
        entityManager.persist(createNewBook(isbn));

        boolean exists = repository.existsByIsbn(isbn);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro na base com isbn informado")
    public void returnFalseWhenIsbnDoeExist() {
        String isbn = "1234";

        boolean exists = repository.existsByIsbn(isbn);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve obter um livro por id")
    public void findByIdTest() {
        Book book = createNewBook("123");
        entityManager.persist(book);
        Optional<Book> foundBook = repository.findById(book.getId());
        assertThat(foundBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        Book book = createNewBook("321");

        Book savedBook = repository.save(book);

        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve Deletar um livro")
    public void deleteBookTest() {
        Book book = createNewBook("321");
        entityManager.persist(book);

        Book foundBook = entityManager.find(Book.class, book.getId());

        repository.delete(foundBook);

        Book deletedBook = entityManager.find(Book.class, book.getId());

        assertThat(deletedBook).isNull();
    }

    public static Book createNewBook(String isbn) {
        return Book.builder()
                .author("Fulano")
                .title("As aventuras")
                .isbn(isbn)
                .build();
    }
}
