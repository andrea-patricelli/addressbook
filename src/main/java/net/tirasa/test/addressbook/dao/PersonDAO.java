package net.tirasa.test.addressbook.dao;

import java.util.List;
import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.exceptions.DatabaseException;

public interface PersonDAO {
    
    public Person save(Person person) throws DatabaseException;

    public Person find(long id) throws DatabaseException;
    
    public List<Person> list() throws DatabaseException;

    public void delete(long id) throws DatabaseException;
}
