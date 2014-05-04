package com.wcb.dao;

/**
 * Exception when db error.
 * 
 * @author King wang
 */
public class DbException extends RuntimeException {

    public DbException() {
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

}
