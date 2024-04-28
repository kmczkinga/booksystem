package hu.nye.progkor.booksystem.populator;

/**
 * Adatbázis populátor interface.
 */
public interface DBPopulator {

    /**
     * Betölti a megadott adatokat az adatbázisba.
     */
    void populateDatabase();
}