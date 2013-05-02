package net.tirasa.test.addressbook.exceptions;

import java.util.logging.Logger;
import net.tirasa.test.addressbook.dao.impl.PersonDAOJdbcImpl;
import org.slf4j.LoggerFactory;

public class DatabaseException extends Exception {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(PersonDAOJdbcImpl.class);

    private static final long serialVersionUID = -7577600787915618669L;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
        logger.info(cause.getMessage());
    }
}
