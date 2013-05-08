package net.tirasa.test.addressbook.dao;

import java.util.List;
import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.exceptions.DatabaseException;

public interface PersonDAO {
    
    public void save(String id, String name, String email, String telephone) throws DatabaseException;

    public Person find(String id) throws DatabaseException;

    public List<Person> list() throws DatabaseException;

    public void delete(String id) throws DatabaseException;
}
