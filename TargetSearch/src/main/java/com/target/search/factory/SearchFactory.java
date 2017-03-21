package com.target.search.factory;

import com.target.search.base.SearchInterface;
import com.target.search.base.impl.IndexedSearch;
import com.target.search.base.impl.RegularExpressionSearch;
import com.target.search.base.impl.StringSearch;

/**
 * Created by nikhil on 3/14/2017.
 */

/**
 * This class is a factory with a static method that returns the Search object that needs to be used
 * based on the searchType provided.
 */
public class SearchFactory {

    public static SearchInterface search(String searchType) {

        if ("String Search".equals(searchType))
            return new StringSearch();
        else if ("Regular Expression Search".equals(searchType))
            return new RegularExpressionSearch();
        else if ("Indexed Search".equals(searchType))
            return new IndexedSearch();

        return null;
    }


}
