package hu.unideb.inf;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilsTest {


    @Test
    void getPersons() {
        List<Person> testfelhasznalo = DBUtils.getPersons();
        assertEquals(DBUtils.getPersons(), testfelhasznalo);
    }

    @Test
    void getFeladatok() {
        List<Feladatok> testfeladat = DBUtils.getFeladatok();
        assertEquals(DBUtils.getFeladatok(), testfeladat);
    }

    Feladatok feladat = new Feladatok("ők", "mi", "ja");

    @Test
    void addFeladatok() {
        int allapot = DBUtils.feladatok.size();
        DBUtils.addFeladatok(feladat);
        Assert.assertEquals(allapot + 1, DBUtils.feladatok.size());
        Assert.assertEquals(allapot, DBUtils.feladatok.size());
    }
}