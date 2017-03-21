package com.target.search.util;

import java.util.HashMap;

/**
 * Created by nikhil on 3/14/2017.
 */
public class SearchUtil {

    public static int SIMPLE_SEARCH = 1, REGULAR_EXPRESSION_SEARCH = 2, INDEXED_SEARCH = 3;
    private static HashMap<Integer, String> inputTypeMap = new HashMap<>();

    public static HashMap<Integer, String> getSearchTypeMap() {
        inputTypeMap.put(0, "Exit");
        inputTypeMap.put(SIMPLE_SEARCH, "String Search");
        inputTypeMap.put(REGULAR_EXPRESSION_SEARCH, "Regular Expression Search");
        inputTypeMap.put(INDEXED_SEARCH, "Indexed Search");
        inputTypeMap.put(4, "Indexed Search - create or update index");
        return inputTypeMap;
    }

}
