package com.target.search.exception;

/**
 * Created by nikhil on 3/17/2017.
 */

/**
 * This is a custom exception used to handle exceptions in the application.
 */
public class SearchException extends Exception {

    private static final long serialVersionUID = 1L;

    public SearchException(String errorMessage) {
        super(errorMessage);
    }

    public SearchException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}
