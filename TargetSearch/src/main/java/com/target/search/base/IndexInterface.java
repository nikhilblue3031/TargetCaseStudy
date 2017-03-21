package com.target.search.base;

import com.target.search.exception.SearchException;

import java.io.IOException;

/**
 * Created by nikhil on 3/19/2017.
 */

/**
 * This interface provides the method to create or update index for the files.
 */
public interface IndexInterface {

    public void createIndex(String directoryPath) throws SearchException, IOException;

}
