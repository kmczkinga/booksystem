package hu.nye.progkor.booksystem.populator.impl;

import java.util.List;

import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.populator.DBPopulator;
import hu.nye.progkor.booksystem.repository.BookRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * A szerver indítás utáni könyvek létrehozásáért felelős osztály.
 */
@Component
@Order(1)
public class BookInitalizer implements DBPopulator {

    private static final Book BOOK1 = new Book("9789632521275", "Anyegin", 216, 940);
    private static final Book BOOK2 = new Book("9789635047079", "Szabadság árva magvetője", 189, 3799);
    private static final Book BOOK3 = new Book("9789632522159", "Egri csillagok", 531, 1700);
    private static final Book BOOK4 = new Book("9789639503397", "Az arany ember", 156, 1795);

    private static final List<Book> BOOKS = List.of(BOOK1, BOOK2, BOOK3, BOOK4);

    private final BookRepository bookRepository;

    public BookInitalizer(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Elmenti a könyveket a bookRepositoryval.
     */
    @Override
    public void populateDatabase() {
        bookRepository.saveAll(BOOKS);
    }

}
