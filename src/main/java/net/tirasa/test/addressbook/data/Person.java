package net.tirasa.test.addressbook.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = -6096955359736734618L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String email;

    private String telephone;

    public Person() {
    }

    public Person(String name, String email, String cellPhoneNumber) {

        this.name = name;
        this.email = email;
        this.telephone = cellPhoneNumber;

    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCellPhoneNumber() {
        return this.telephone;
    }

//    @Override
//    public String toString() {
//        return "Person [id=" + id + ", name=" + name + ", email=" + email
//                + ", telephone=" + telephone + "]";
//    }
}
