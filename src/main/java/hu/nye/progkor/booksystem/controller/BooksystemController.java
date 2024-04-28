package hu.nye.progkor.booksystem.controller;

import java.util.List;
import java.util.Optional;

import hu.nye.progkor.booksystem.model.Author;
import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.service.AuthorService;
import hu.nye.progkor.booksystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller osztály, amelyen a frontend eléri a backendet.
 */
@Controller
@RequestMapping(value = "/booksystem")
public class BooksystemController {

    private final AuthorService authorService;

    private final BookService bookService;

    @Autowired
    public BooksystemController(final AuthorService authorService, final BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    /**
     * Visszaadja az összes szerzőt, amelyet megkapt a service-től. Beállítja a
     * modelben a szerzőket. Visszaadja a listázós oldalhoz szükséges Stringet.
     */
    @GetMapping
    public String findAll(final Model model) {
        final List<Author> authors = this.authorService.findAll();
        model.addAttribute("authors", authors);
        return "booksystem/list";
    }

    /**
     * Betölti az adott id-jű szerzőt, beállítja a modelnek, visszaadja a szerző
     * módosító oldal elérését.
     */
    @GetMapping(value = "/author/{id}")
    public String loadAuthor(@PathVariable Long id, final Model model) {
        final Author author = this.authorService.load(id);
        model.addAttribute("author", author);
        return "booksystem/author/edit";
    }

    /**
     * Visszaadja a szerző létrehozó form elérését.
     */
    @GetMapping("/author/create")
    public String createAuthorForm(final Model model) {
        return "booksystem/author/create";
    }

    /**
     * Elmenti az adott szerzőt. Meghívja a findAll-t.
     */
    @PostMapping("/author")
    public String createAuhtor(final Model model, final Author author) {
        this.authorService.save(author);
        return findAll(model);
    }

    /**
     * Frissíti a megadott id-jű szerzőt. Meghívja a findAll-t.
     */
    @PostMapping(value = "/author/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateAuthor(final Model model, @RequestParam(value = "id", required = false) Long id,
            final Author authorChanges) {
        this.authorService.update(id, authorChanges);
        return findAll(model);
    }

    /**
     * Kitörli a megadott id-jű szerzőt. Meghívja a findAll-t.
     */
    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(final @PathVariable("id") Long id, final Model model) {
        this.authorService.delete(id);
        return findAll(model);
    }

    /**
     * Visszaad egy könyvet az isbn alapján. Hozzáadja a modelhez, meghívja a
     * findAllt, majd visszaadja a könyv módosító oldalt.
     */
    @GetMapping(value = "/book/{isbn}")
    public String loadBook(@PathVariable String isbn, final Model model) {
        final Book book = bookService.load(isbn);
        model.addAttribute("book", book);
        findAll(model);
        return "booksystem/book/edit";
    }

    /**
     * Betölti az összes szerzőt, hozzáadja a modelhez, majd visszaadja a könyv
     * létrehozós oldal elérését.
     */
    @GetMapping("/book/create")
    public String createBookForm(final Model model) {
        final List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "booksystem/book/create";
    }

    /**
     * Létrehoz egy új könyvet. Betölti a régi szerzőt, hozzáadja a könyvet és azt
     * is elmenti. Meghívja a findAll-t.
     */
    @PostMapping("/book")
    public String createBook(final Model model, final Book book, final Long author) {
        this.bookService.save(book);
        final Author oldAuthor = authorService.load(author);
        oldAuthor.getBooks().add(book);
        authorService.save(oldAuthor);
        return findAll(model);
    }

    /**
     * A megadott isbn, könyv változtatások és szerző id alapján frissíti a könyv
     * adatait. Ehhez betölti a régi könyvet, lekérdezi azt a szerzőt, akié a könyv.
     * Ha létezik ilyen szerző, akkor kitörli tőle a könyvet, majd elmenti azt a
     * szerzőt. Majd frissíti a könyv adatait, hozzáadja a könyvet a szerzőhöz és
     * elmenti azokat. Meghívja a findAll metódust.
     */
    @PostMapping(value = "/book/{isbn}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateBook(final Model model, @RequestParam(value = "isbn", required = false) String isbn,
            final Book bookChanges, final Long author) {
        Book oldBook = bookService.load(isbn);
        authorService.removeBookFromAuthor(oldBook);
        final Book book = bookService.update(oldBook, bookChanges);
        Author oldAuthor = authorService.load(author);
        oldAuthor.getBooks().add(book);
        authorService.save(oldAuthor);
        return findAll(model);
    }

    /**
     * A megkapott isbn alapján kitörli a könyvet a rendszerből. Ehhez betölti a
     * rendszerből a könyvet, hogy a szerzőtől is kitörölje. Meghívja a findAll
     * metódust.
     */
    @GetMapping("/book/delete/{isbn}")
    public String deleteBook(final @PathVariable("isbn") String isbn, final Model model) {
        Book book = bookService.load(isbn);
        authorService.removeBookFromAuthor(book);

        return findAll(model);
    }
}
