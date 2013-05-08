package net.tirasa.test.addressbook.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "Person") // o Persons
public class Person implements Serializable {

    private static final long serialVersionUID = -6096955359736734618L;

    @Id
    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="telephone")
    private String telephone;

    // Constructor
    public Person() {
    }

    public Person(String name, String email, String cellPhoneNumber) {

        this.name = name;
        // this.surname = surname;
        this.email = email;
        // this.homePhoneNumber = homePhoneNumber;
        this.telephone = cellPhoneNumber;

    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return null; // this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCellPhoneNumber() {
        return this.telephone;
    }

    public String getHomePhoneNumber() {
        return null; // this.homePhoneNumber;
    }
}
