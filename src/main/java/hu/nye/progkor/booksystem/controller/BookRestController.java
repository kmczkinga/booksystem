package hu.nye.progkor.booksystem.controller;

import java.util.List;

import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController osztály, amelyen keresztül meghívhatjuk a könyvel kapcsolatos
 * endpointokat.
 */
@RestController
@RequestMapping(value = "/api/v1/booksystem/book")
public class BookRestController {

    private final BookService bookSystemService;

    @Autowired
    public BookRestController(BookService bookSystem) {
        this.bookSystemService = bookSystem;
    }

    /**
     * Visszaadja az összes könyvet.
     */
    @GetMapping
    public List<Book> findAllBook() {
        return this.bookSystemService.findAll();
    }

    /**
     * Betölti az adott isbn-ű könyvet.
     */
    @GetMapping(value = "/{isbn}")
    public Book load(@PathVariable("id") String isbn) {
        return this.bookSystemService.load(isbn);
    }

    /**
     * Elmenti a könyvet.
     */
    @PostMapping
    public Book save(@RequestBody Book book) {
        return this.bookSystemService.save(book);
    }

    /**
     * Módosítja az adott isbn-ű könyvet.
     */
    @PutMapping(value = "/{isbn}")
    public Book update(@PathVariable("isbn") String isbn, @RequestBody Book bookChanges) {
        final Book book = bookSystemService.load(isbn);
        return this.bookSystemService.update(book, bookChanges);
    }

    /**
     * Kitörli az adott isbn-ű könyvet.
     */
    @DeleteMapping(value = "{isbn}")
    public ResponseEntity<Void> delete(@PathVariable("isbn") String isbn) {
        this.bookSystemService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
