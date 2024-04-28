package hu.nye.progkor.booksystem.controller;

import java.util.List;

import hu.nye.progkor.booksystem.model.Author;
import hu.nye.progkor.booksystem.service.AuthorService;
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
 * RestController osztály, amely segítségével elérhetjük a szerzőkkel
 * kapcsolatos endpointokat.
 */
@RestController
@RequestMapping(value = "/api/v1/booksystem/author")
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Betölti az összes szerzőt.
     */
    @GetMapping
    public List<Author> findAll() {
        return this.authorService.findAll();
    }

    /**
     * Visszaadja az adott id-jű szerzőt.
     */
    @GetMapping(value = "/{id}")
    public Author load(@PathVariable("id") Long id) {
        return this.authorService.load(id);
    }

    /**
     * Elmenti a szerzőt.
     */
    @PostMapping
    public Author save(@RequestBody Author author) {
        return this.authorService.save(author);
    }

    /**
     * Módosítja az adott id-jű szerzőt.
     */
    @PutMapping(value = "/{id}")
    public Author update(@PathVariable("id") Long id, @RequestBody Author authorChanges) {
        return this.authorService.update(id, authorChanges);
    }

    /**
     * Kitörli az adott id-jű szerzőt.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
