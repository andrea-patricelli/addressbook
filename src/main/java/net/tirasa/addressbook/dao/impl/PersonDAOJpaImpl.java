package net.tirasa.addressbook.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import net.tirasa.addressbook.dao.PersonDAO;
import net.tirasa.addressbook.data.Person;
import net.tirasa.addressbook.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//@Service
public class PersonDAOJpaImpl implements PersonDAO {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    @Autowired
    protected EntityManager entityManager;

    private final static Logger LOG = LoggerFactory.getLogger(PersonDAOJpaImpl.class);

    @Override
    public Person save(Person person) throws DatabaseException {
        LOG.debug("EXECUTING SAVE OPERATION ON: ".concat(person.getName()));
        try {
            return entityManager.merge(person);
        } catch (Exception e) {
            LOG.error("ERROR DURING SAVE OPERATION: ".concat(e.getMessage()));
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Person find(long id) throws DatabaseException {
        Person p = null;
        try {
            p = entityManager.find(Person.class, id);
        } catch (IllegalArgumentException e) {
            LOG.error("FIND OPERATION BY ENTITY MANAGER FAILED");
            throw new DatabaseException(e.getCause());
        }
        return p;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> list() throws DatabaseException {
        LOG.debug("LISTING PERSONS IN DATABASE...");
        List<Person> resultList = null;
        try {
            TypedQuery<Person> query = entityManager.createQuery("Select a From Person a", Person.class);
            resultList = query.getResultList();
        } catch (IllegalArgumentException e) {
            LOG.error("LIST OPERATION BY ENTITY MANAGER FAILED");
            throw new DatabaseException(e.getCause());
        }
        return resultList;
    }

    @Override
    public void delete(long id) throws DatabaseException {
        LOG.debug("DELETING ENTRY WITH ID: " + id);
        try {
            entityManager.remove(entityManager.find(Person.class, id));
        } catch (IllegalArgumentException e) {
            LOG.error("DELETE OPERATION BY ENTITY MANAGER FAILED: PROBLEM ON ARGUMENTS");
            throw new DatabaseException(e.getCause());
        }
    }
}
