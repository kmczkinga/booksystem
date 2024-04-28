package hu.nye.progkor.booksystem.populator.impl;

import java.time.LocalDate;
import java.util.List;

import hu.nye.progkor.booksystem.model.Author;
import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.populator.DBPopulator;
import hu.nye.progkor.booksystem.repository.AuthorRepository;
import hu.nye.progkor.booksystem.repository.BookRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A szerver indítás utáni szerző példányok létrehozásáért felelős osztály.
 */
@Component
@Order(2)
public class AuthorInitalizer implements DBPopulator {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorInitalizer(final AuthorRepository authorRepository, final BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Lekérdezi az összes könyvet a bookRepositorytól, majd azokat beállítja a
     * megfelelő szerzőknek. Elmenti a szerzőket.
     */
    @Override
    public void populateDatabase() {
        final List<Book> books = bookRepository.findAll();
        final List<Author> authors = List.of(
                new Author(1L, "Géza", "Gárdonyi", LocalDate.of(1863, 8, 3), "Magyar", books.subList(2, 3)),
                new Author(2L, "Szergejevics Puskin", "Alekszandr ", LocalDate.of(1799, 6, 6), "Orosz",
                        books.subList(0, 2)),
                new Author(3L, "Mór", "Jókai", LocalDate.of(1825, 2, 18), "Magyar", books.subList(3, 4)));
        authorRepository.saveAll(authors);
    }

}
