package net.tirasa.addressbook.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.wicket.util.io.IClusterable;

@Entity
public class Person implements IClusterable {

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

    public Person(final Person comment) {
        this.name = comment.name;
        this.email = comment.email;
        this.telephone = comment.telephone;
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

    public String getTelephone() {
        return this.telephone;
    }

    public void setId(long newId) {
        this.id = newId;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setTelephone(String newTelephone) {
        this.telephone = newTelephone;
    }
}
