package hu.nye.progkor.booksystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Könyv osztály, amely egy fizikai könyv adatait tárolja.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Book {

    @Id
    @Column(name = "isbn", length = 13)
    private String isbn;

    @Column
    private String title;

    @Column
    private Integer numberOfPages;

    @Column
    private Integer price;

    /**
     * Beállítja a paraméterben megkapott értékeket a jelenlegi könyvnek. Ez
     * használatos az update-hez.
     */
    public void apply(Book bookChanges) {
        this.isbn = bookChanges.getIsbn();
        this.title = bookChanges.getTitle();
        this.numberOfPages = bookChanges.getNumberOfPages();
        this.price = bookChanges.getPrice();
    }
}
