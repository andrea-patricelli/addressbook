package net.tirasa.addressbook.exceptions;

public class DatabaseException extends Exception {


    private static final long serialVersionUID = -7577600787915618669L;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
