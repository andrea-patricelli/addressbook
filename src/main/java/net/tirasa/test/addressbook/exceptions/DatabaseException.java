package net.tirasa.test.addressbook.exceptions;

public class DatabaseException extends Exception {

    private static final long serialVersionUID = -7577600787915618669L;

    public DatabaseException(String message) {
        super(message);
        System.err.println(message);
    }
}
