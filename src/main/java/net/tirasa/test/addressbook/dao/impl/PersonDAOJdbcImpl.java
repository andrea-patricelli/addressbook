package net.tirasa.test.addressbook.dao.impl;

import java.util.List;
import net.tirasa.test.addressbook.dao.PersonDAO;
import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.exceptions.DatabaseException;
import net.tirasa.test.addressbook.mapper.PersonMapper;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAOJdbcImpl implements PersonDAO {
    
    @Autowired
    private BasicDataSource dataSource;

    @Override
    public void save(String id, String name, String email, String telephone) throws DatabaseException {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(this.dataSource);
        Person searched = this.find(id); // if it doesn't work use name instead of id
        StringBuilder SQL = new StringBuilder();
        try {
            if (searched != null) {
                SQL.append("UPDATE Persons SET Name='").append(name).append("', Email='").append(email).append(
                        "', Telephone='").append(telephone).append("' WHERE Name='").append(id).append('\'');
                jdbcTemplateObject.update(SQL.toString());
            } else {
                SQL.append("INSERT INTO Persons (Name, Email, Telephone) VALUES ('").append(name).append("', '").
                        append(email).append("', '").append(telephone).append("')");
                jdbcTemplateObject.update(SQL.toString());
            }
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Person find(String requestParam_id) throws DatabaseException {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(this.dataSource);
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT * FROM Persons WHERE Name='").append(requestParam_id).append('\'');
        Person searched = null;
        try {
            List<Person> searchedPersons = jdbcTemplateObject.query(SQL.toString(), new PersonMapper());
            // check if returned list is empty, if not get the first (and only) element of Person type returned
            if (!searchedPersons.isEmpty()) {
                searched = searchedPersons.iterator().next();
            }
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
        return searched;
    }

    @Override
    public List<Person> list() throws DatabaseException {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(this.dataSource);
        List<Person> collection;
        String SQL = "SELECT * FROM Persons";
        try {
            collection = jdbcTemplateObject.query(SQL, new PersonMapper());
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
        return collection;
    }

    @Override
    public void delete(String requestParam_id) throws DatabaseException {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(this.dataSource);
        StringBuilder SQL = new StringBuilder();
        try {
            jdbcTemplateObject.update(SQL.append("DELETE FROM Persons WHERE Name ='").append(requestParam_id).append(
                    '\'').
                    toString());
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }
}