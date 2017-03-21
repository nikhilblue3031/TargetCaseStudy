package com.target.search.base;

import com.target.search.exception.SearchException;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by nikhil on 3/14/2017.
 */

/**
 * This interface provides the method for search process and count matches form the search.
 */
public interface SearchInterface {

    public TreeMap<String, Long> countMatches(String directoryPath, String searchString) throws SearchException, IOException;


}

