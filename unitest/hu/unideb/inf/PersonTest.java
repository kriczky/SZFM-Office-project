package hu.unideb.inf;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void getId() {
        int id = 4;
        Person person = new Person(id,"akarmi","valmi");
        assertEquals(person.getId(),id+1);
    }

    @Test
    void getUsername() {
        String nev = "akarmi";
        Person person = new Person(2,nev,"valmi");
        assertEquals(person.getUsername(),nev);
    }

    @Test
    void getCompany_id() {
        String id = "valami";
        Person person = new Person(3,"akarmi",id);
        assertEquals(person.getCompany_id(),id);
    }

    @Test
    void testToString() {
        Person person = new Person(4,"akarmi","valmi");
        String v = person.toString();

        assertEquals(person.toString(),v);
    }
}