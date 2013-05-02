package net.tirasa.test.addressbook.dao;

import java.util.List;
import net.tirasa.test.addressbook.exceptions.DatabaseException;

public interface PersonOperations<T> {

    public void addEntry(String id, String name, String email, String telephone) throws DatabaseException;

    public T searchEntry(String requestParam_id) throws DatabaseException;

    public List<T> listAll() throws DatabaseException;

    public void deleteEntry (String requestParam_id) throws DatabaseException;
}
