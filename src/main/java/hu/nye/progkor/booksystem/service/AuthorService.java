package hu.nye.progkor.booksystem.service;

import java.util.List;
import java.util.Optional;

import hu.nye.progkor.booksystem.model.Author;
import hu.nye.progkor.booksystem.model.Book;
import hu.nye.progkor.booksystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;

/**
 * Service osztály, amely a könyvekkel kapcsolatos üzleti logikáért felelős. A
 * bookRepository-t használja az adatbázissal való kommunikációhoz.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Lekérdezi az összes tárolt szerzőt.
     */
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    /**
     * Az adott id alapján lekérdezi az adott szerzőt.
     */
    public Author load(Long id) {
        return authorRepository.getById(id);
    }

    /**
     * Elmenti a megadott szerzőt, majd a mentett szerzőt visszaadja paraméterként.
     */
    public Author save(Author author) {
        authorRepository.save(author);
        return authorRepository.getById(author.getId());
    }

    /**
     * Az adatbázisban elmentett szerzőt felülírja az új szerzőt adataival.
     * Használja a Author.apply-t is, ahol történnek a mezőbeállítások.
     */
    public Author update(Long id, Author authorChanges) {
        final Author author = load(id);
        author.apply(authorChanges);
        authorRepository.save(author);
        return author;
    }

    /**
     * Az id alapján kitörli a szerzőt.
     */
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    /**
     * Visszaadja azt a szerzőt, amelyhez tartozik az adott isbn-ű könyv. Lekérdezi
     * az összes szerzőt, leszűri azokra a szerzőkre akikhez tartozik a könyv, majd
     * egyet visszaad.
     */
    public Optional<Author> getAuthorByBookIsbn(final String bookIsbn) {
        return findAll().stream().filter(author -> {
            for (Book book : author.getBooks()) {
                if (bookIsbn.equals(book.getIsbn())) {
                    return true;
                }
            }
            return false;
        }).findAny();
    }

    /**
     * Kitöri a megadott könyvet a hozzá tartozó szerzőtől. Lekérdezi az összes
     * szerzőt, leszűri azokra, akiké a könyv, kitörli a könyvet onnan, majd elmenti
     * a szerzőt.
     */
    public void removeBookFromAuthor(final Book book) {
        Optional<Author> authorWithBookOptional = findAll().stream().filter(author -> author.getBooks().contains(book))
                .findFirst();
        if (authorWithBookOptional.isPresent()) {
            Author authorWithBook = authorWithBookOptional.get();
            authorWithBook.getBooks().remove(book);
            authorRepository.save(authorWithBook);
        }
    }
}
