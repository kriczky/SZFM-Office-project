package hu.unideb.inf;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeladatokTest {

    @Test
    void getNevek() {
        String nev = "barmi";
        Feladatok feladatok = new Feladatok(nev, "te", "hasonló");
        assertEquals(feladatok.getNevek(), nev);
    }

    @Test
    void getFeladat() {
        String feladat = "barmi";
        Feladatok feladatok = new Feladatok("te", feladat, "hasonló");
        assertEquals(feladatok.getFeladat(), feladat);
    }

    @Test
    void getCeg_id() {
        String id = "barmi";
        Feladatok feladatok = new Feladatok("nev", "akarmi", id);
        assertEquals(feladatok.getCeg_id(), id + "2");

    }
}