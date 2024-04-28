package hu.nye.progkor.booksystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hu.nye.progkor.booksystem.model.Author;
import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.repository.AuthorRepository;
import hu.nye.progkor.booksystem.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorServiceTest {

    private static final Book BOOK1 = new Book("9789632521275", "Anyegin", 216, 940);
    private static final Book BOOK2 = new Book("9789635047079", "Szabadság árva magvetője", 189, 3799);
    private static final Book BOOK3 = new Book("9789632522159", "Egri csillagok", 531, 1700);
    private static final Book BOOK4 = new Book("9789639503397", "Az arany ember", 156, 1795);

    private static final Author AUTHOR1 = new Author(1L, "Géza", "Gárdonyi", LocalDate.of(1863, 8, 3), "Magyar",
            List.of(BOOK3));
    private static final Author AUTHOR2 = new Author(2L, "Szergejevics Puskin", "Alekszandr ", LocalDate.of(1799, 6, 6),
            "Orosz", List.of(BOOK1, BOOK2));
    private static final Author AUTHOR3 = new Author(3L, "Mór", "Jókai", LocalDate.of(1825, 2, 18), "Magyar",
            List.of(BOOK4));

    private static final Author AUTHOR4 = new Author(2L, "Szergejevics Puskin", "Alekszandr ", LocalDate.of(1799, 6, 6),
            "Orosz", new ArrayList<>(List.of(BOOK1, BOOK2)));

    private static final List<Author> AUTHORS = List.of(AUTHOR1, AUTHOR2, AUTHOR3);

    @Mock
    private AuthorRepository repository;

    private AuthorService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AuthorService(repository);
    }

    @Test
    void findAllShouldReturnAllBooks() {
        // given
        given(repository.findAll()).willReturn(AUTHORS);
        // when

        List<Author> actual = underTest.findAll();
        // then
        assertEquals(AUTHORS, actual);
    }

    @Test
    void loadShouldLoadBookWithIsbn() {
        // given
        given(repository.getById(AUTHOR1.getId())).willReturn(AUTHOR1);
        // when

        Author actual = underTest.load(AUTHOR1.getId());
        // then
        assertEquals(AUTHOR1, actual);
    }

    @Test
    void saveShouldSaveBook() {
        // given
        given(repository.getById(AUTHOR1.getId())).willReturn(AUTHOR1);
        // when

        Author actual = underTest.save(AUTHOR1);
        // then
        assertEquals(AUTHOR1, actual);
        verify(repository).save(AUTHOR1);
    }

    @Test
    void updateShouldUpdateBookWithGivenChanges() {
        // given
        given(repository.getById(AUTHOR1.getId())).willReturn(AUTHOR1);
        Author author = new Author(AUTHOR2.getId(), AUTHOR2.getFirstName(), AUTHOR2.getLastName(),
                AUTHOR2.getBirthDate(), AUTHOR2.getNationality(), AUTHOR1.getBooks());
        // when

        Author actual = underTest.update(AUTHOR1.getId(), AUTHOR2);
        // then
        assertEquals(author, actual);
    }

    @Test
    void deleteShouldDeleteBookWithIsbn() {
        // given

        // when

        underTest.delete(AUTHOR1.getId());
        // then
        verify(repository).deleteById(AUTHOR1.getId());
    }

    @Test
    void getAuthorByBookIsbnShouldReturnAuthorWithBookIsbn() {
        // given
        given(repository.findAll()).willReturn(AUTHORS);
        // when

        Optional<Author> actual = underTest.getAuthorByBookIsbn(BOOK1.getIsbn());
        // then
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), AUTHOR2);
        verify(repository).findAll();
    }

    @Test
    void getAuthorByBookIsbnShouldNotReturnAuthorWithBookIsbn() {
        // given
        given(repository.findAll()).willReturn(List.of(AUTHOR1, AUTHOR2));
        // when

        Optional<Author> actual = underTest.getAuthorByBookIsbn(BOOK4.getIsbn());
        // then
        assertTrue(actual.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void removeBookFromAuthorShouldRemoveBookFromAuthor() {
        // given

        given(repository.findAll()).willReturn(List.of(AUTHOR4));
        // when

        underTest.removeBookFromAuthor(BOOK1);
        // then

        verify(repository).findAll();
        verify(repository).save(any());
    }

    @Test
    void removeBookFromAuthorShouldNotRemoveBookFromAuthor() {
        // given

        given(repository.findAll()).willReturn(List.of(AUTHOR4));
        // when

        underTest.removeBookFromAuthor(BOOK4);
        // then

        verify(repository).findAll();
        verify(repository, never()).save(any());
    }

}
