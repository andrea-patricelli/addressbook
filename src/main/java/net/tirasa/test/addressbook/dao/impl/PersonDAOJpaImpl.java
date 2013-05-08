package net.tirasa.test.addressbook.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import net.tirasa.test.addressbook.dao.PersonDAO;
import net.tirasa.test.addressbook.data.Person;
import net.tirasa.test.addressbook.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonDAOJpaImpl implements PersonDAO {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    @Autowired
    protected EntityManager entityManager;

    private final static Logger LOG = LoggerFactory.getLogger(PersonDAOJpaImpl.class);

    @Transactional
    public void save(String id, String name, String email, String telephone) throws DatabaseException {
        LOG.debug("BEGINNING OF SAVE OPERATION");
        if (find(name) == null) {
            LOG.debug("INSERTING A NEW PERSON WITH ID: ".concat(name));
            entityManager.persist(new Person(name, email, telephone));
        } else {
            LOG.debug("UPDATING EXISTING PERSON: ".concat(name));
            entityManager.merge(new Person(name, email, telephone));
        }
    }

    @Transactional
    public Person find(String id) throws DatabaseException {
        return entityManager.find(Person.class, id);
    }

    @Transactional
    public List<Person> list() throws DatabaseException {
        LOG.debug("LISTING PERSONS IN DATABASE...");
        Query query = entityManager.createQuery("Select a From Person a", Person.class);
        List<Person> resultList = query.getResultList();
        return resultList;
    }

    @Transactional
    public void delete(String id) throws DatabaseException {
        LOG.debug("DELETING ENTRY WITH ID: ".concat(id));
        entityManager.remove(entityManager.find(Person.class, id));
    }
}
