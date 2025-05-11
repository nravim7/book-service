package org.example;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;


    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }


    @Test
    public void should_return_all_books() {
        BookEntity book1 = new BookEntity();
        book1.setId(1L);
        book1.setName("Kannada");
        book1.setAuthor("Ravi");

        BookEntity book2 = new BookEntity();
        book2.setId(2L);
        book2.setName("English");
        book2.setAuthor("Smith");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<BookEntity> result = bookService.getAllBooks();

        assertThat(result).isEqualTo(List.of(book1, book2));
    }

    @Test
    public void should_return_book_for_given_id() {
        BookEntity book1 = new BookEntity();
        book1.setId(1L);
        book1.setName("Kannada");
        book1.setAuthor("Ravi");


        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        BookEntity result = bookService.getBook(1L);

        assertThat(result).isEqualTo(book1);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Kannada");
        assertThat(result.getAuthor()).isEqualTo("Ravi");
    }

    @Test
    public void should_throw_exception_when_book_is_not_found() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getBook(99L));
    }

    @Test
    public void should_save_the_new_book() {
        BookEntity book1 = new BookEntity();
        book1.setId(1L);
        book1.setName("Kannada");
        book1.setAuthor("Ravi");

        bookService.saveBook(book1);
        verify(bookRepository, times(1)).save(book1);
    }


}