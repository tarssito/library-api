package com.tarssito.libraryapi.service;

import com.tarssito.libraryapi.exception.BusinessException;
import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.repository.BookRepository;
import com.tarssito.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService bookService;
    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp() {
        this.bookService = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        Book book = createValidBook();
        Mockito.when(repository.existsByIsbn(book.getIsbn())).thenReturn(false);

        Mockito.when(repository.save(book))
                .thenReturn(Book.builder()
                        .id(123L)
                        .isbn("123")
                        .author("Fulano")
                        .title("As Aventuras")
                        .build())
        ;

        Book savedBook = bookService.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
        assertThat(savedBook.getTitle()).isEqualTo("As Aventuras");
    }

    @Test
    @DisplayName("Deve lançar erro de negócio ao tentar salvar um livro com ISBN duplicado")
    public void shouldNotSaveBookWithDuplicatedISBN() {
        Book book = createValidBook();
        Mockito.when(repository.existsByIsbn(book.getIsbn())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable(() -> bookService.save(book));

        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado.");

        Mockito.verify(repository, Mockito.never()).save(book);

    }

    @Test
    @DisplayName("Deve obter um livro por id")
    public void getByIdTest() {
        Long id = 1L;

        Book book = createValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> optionalBook = bookService.getByID(id);

        assertThat(optionalBook.isPresent()).isTrue();
        assertThat(optionalBook.get().getId()).isEqualTo(id);
        assertThat(optionalBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(optionalBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(optionalBook.get().getIsbn()).isEqualTo(book.getIsbn());

    }

    @Test
    @DisplayName("Deve retornar vazio ao obter um livro por id quando ele não existe na base.")
    public void bookNotFoundByIdTest() {
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> optionalBook = bookService.getByID(id);

        assertThat(optionalBook.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar atualizar um livro sem ID")
    public void shouldNotUpdateBookWithoutID() {
        Book book = createValidBook();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.update(book));

        Mockito.verify(repository, Mockito.never()).save(book);
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    public void updateBookTest() {
        Book book = createValidBook();
        book.setId(123L);
        book.setAuthor("Ciclano");
        Mockito.when(repository.save(book))
                .thenReturn(Book.builder()
                        .id(123L)
                        .isbn("123")
                        .author("Ciclano")
                        .title("As Aventuras")
                        .build())
        ;
        Book savedBook = bookService.update(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("Ciclano");
        assertThat(savedBook.getTitle()).isEqualTo("As Aventuras");
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar deletar um livro sem ID")
    public void shouldNotDeleteBookWithoutID() {
        Book book = new Book();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.delete(book));

        Mockito.verify(repository, Mockito.never()).delete(book);
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest() {
        Book book = Book.builder().id(321L).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> bookService.save(book));
        Mockito.verify(repository, Mockito.times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve filtrar livros pelas propriedades")
    public void findBookTest() {
        // cenario
        Book book = createValidBook();

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Book> books = Collections.singletonList(book);
        Page<Book> page = new PageImpl<>(books, pageRequest, 1);
        Mockito.when( repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        // execução
        Page<Book> result = bookService.find(book, pageRequest);

        // verificação
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(books);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    private Book createValidBook() {
        return Book.builder()
                .isbn("123")
                .author("Fulano")
                .title("As Aventuras")
                .build();
    }
}
