package net.tirasa.test.addressbook.data;

public class Person {

    private String name = "";

    private String surname = "";

    private String email = "";

    private String cellPhoneNumber = "";

    private String homePhoneNumber = "";

    // Constructor
    public Person() {
    }

    public Person(String name, String surname, String email, String cellPhoneNumber,
            String homePhoneNumber) {

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.homePhoneNumber = homePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;

    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCellPhoneNumber() {
        return this.cellPhoneNumber;
    }

    public String getHomePhoneNumber() {
        return this.homePhoneNumber;
    }
}
