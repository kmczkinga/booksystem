package hu.nye.progkor.booksystem.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Szerző osztály, amely egy szerző adatait, könyveit tárolja.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String nationality;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    /**
     * Beállítja a paraméterben megkapott értékeket a jelenlegi szerzőnek. Ez
     * használatos az update-hez.
     */
    public void apply(Author authorChanges) {
        this.id = authorChanges.getId();
        this.firstName = authorChanges.getFirstName();
        this.lastName = authorChanges.getLastName();
        this.birthDate = authorChanges.getBirthDate();
        this.nationality = authorChanges.getNationality();
    }
}
