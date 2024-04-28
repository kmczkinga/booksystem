package hu.nye.progkor.booksystem.populator;

import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * A különböző adatbázis populátoroknak a meghívásáért felelős.
 */
@Service
public class DBPopulatorService {

    private final List<DBPopulator> dbPopulators;

    public DBPopulatorService(final List<DBPopulator> dbPopulators) {
        this.dbPopulators = dbPopulators;
    }

    /**
     * Meghívja az összes populátor populateDatabase metódusát.
     */
    @PostConstruct
    public void populateDatabase() {
        dbPopulators.forEach(DBPopulator::populateDatabase);
    }

}
