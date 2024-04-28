package hu.nye.progkor.booksystem.repository;

import hu.nye.progkor.booksystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Könyv repository, amivel az adatbázis Book táblájával kommunikálunk.
 */
public interface BookRepository extends JpaRepository<Book, String> {
}
