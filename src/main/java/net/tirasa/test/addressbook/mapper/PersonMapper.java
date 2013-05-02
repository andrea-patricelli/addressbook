package net.tirasa.test.addressbook.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.tirasa.test.addressbook.data.Person;
import org.springframework.jdbc.core.RowMapper;

public class PersonMapper implements RowMapper<Person> {

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getString("Name"), "", rs.getString("Email"), "", rs.getString("Telephone"));
    }
}