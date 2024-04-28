package hu.nye.progkor.booksystem.service;

import java.util.List;

import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * Service osztály, amely a könyvekkel kapcsolatos üzleti logikáért felelős. A
 * bookRepository-t használja az adatbázissal való kommunikációhoz.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Lekérdezi az összes tárolt könyvet.
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Az adott isbn alapján lekérdezi az adott könyvet.
     */
    public Book load(final String isbn) {
        return bookRepository.getById(isbn);
    }

    /**
     * Elmenti a megadott könyvet, majd a mentett könyvet visszaadja paraméterként.
     */
    public Book save(final Book book) {
        bookRepository.save(book);
        return bookRepository.getById(book.getIsbn());
    }

    /**
     * Az adatbázisban elmentett könyvet felülírja az új könyv adataival. Használja
     * a Book.apply-t is, ahol történnek a mezőbeállítások.
     */
    public Book update(final Book oldBook, final Book bookChanges) {
        oldBook.apply(bookChanges);
        bookRepository.save(oldBook);
        return oldBook;
    }

    /**
     * Az isbn alapján kitörli a könyvet.
     */
    public void delete(final String isbn) {
        bookRepository.deleteById(isbn);
    }
}
