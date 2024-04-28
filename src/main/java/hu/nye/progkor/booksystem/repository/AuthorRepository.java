package hu.nye.progkor.booksystem.repository;

import hu.nye.progkor.booksystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Szerző repository, amivel az adatbázis Author táblájával kommunikálunk.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
