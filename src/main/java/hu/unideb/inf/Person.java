package org.example;

public class Person {
    int id;
    String username;
    String company_id;

    public Person(int id, String username, String company_id) {
        this.id = id;
        this.username = username;
        this.company_id = company_id;
    }

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }


    public String getCompany_id() {
        return company_id;
    }


    @Override
    public String toString() {
        return id + " " + username + " " + company_id;
    }
}
