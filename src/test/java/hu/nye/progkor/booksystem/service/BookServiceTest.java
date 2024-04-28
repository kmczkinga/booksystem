package hu.nye.progkor.booksystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.repository.BookRepository;

public class BookServiceTest {

    private static final Book BOOK1 = new Book("9789632521275", "Anyegin", 216, 940);
    private static final Book BOOK2 = new Book("9789635047079", "Szabadság árva magvetője", 189, 3799);
    private static final Book BOOK3 = new Book("9789632522159", "Egri csillagok", 531, 1700);
    private static final Book BOOK4 = new Book("9789639503397", "Az arany ember", 156, 1795);

    private static final List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3, BOOK4);

    @Mock
    private BookRepository repository;

    private BookService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new BookService(repository);
    }

    @Test
    void findAllShouldReturnAllBooks() {
        // given
        given(repository.findAll()).willReturn(BOOKS);
        // when

        List<Book> actual = underTest.findAll();
        // then
        assertEquals(BOOKS, actual);
    }

    @Test
    void loadShouldLoadBookWithIsbn() {
        // given
        given(repository.getById(BOOK1.getIsbn())).willReturn(BOOK1);
        // when

        Book actual = underTest.load(BOOK1.getIsbn());
        // then
        assertEquals(BOOK1, actual);
    }

    @Test
    void saveShouldSaveBook() {
        // given
        given(repository.getById(BOOK1.getIsbn())).willReturn(BOOK1);
        Book book = new Book(BOOK1.getIsbn(), BOOK2.getTitle(), BOOK2.getNumberOfPages(), BOOK2.getPrice());
        // when

        Book actual = underTest.save(BOOK1);
        // then
        assertEquals(book, actual);
        verify(repository).save(BOOK1);
    }

    @Test
    void updateShouldUpdateBookWithGivenChanges() {
        // given

        // when

        Book actual = underTest.update(BOOK1, BOOK2);
        // then
        assertEquals(BOOK2, actual);
    }

    @Test
    void deleteShouldDeleteBookWithIsbn() {
        // given

        // when

        underTest.delete(BOOK1.getIsbn());
        // then
        verify(repository).deleteById(BOOK1.getIsbn());
    }
}
