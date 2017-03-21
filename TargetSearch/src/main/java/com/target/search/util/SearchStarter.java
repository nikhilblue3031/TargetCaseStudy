package com.target.search.util;

import com.target.search.base.SearchInterface;
import com.target.search.base.impl.IndexedSearch;
import com.target.search.exception.SearchException;
import com.target.search.factory.SearchFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.TreeMap;

/**
 * Created by nikhil on 3/20/2017.
 */

/**
 * This class gets the search type and invokes the search process based on the search type provided.
 */
public class SearchStarter {

    private static String directoryPath = "C:\\sample_text";
    private static SearchFactory searchFactory = new SearchFactory();
    private static SearchInterface searchMethod = null;

    /**
     * This method gets the search input entered by user and invokes the
     * search process based on the search input.
     *
     * @param searchString
     * @param searchType
     * @throws IOException
     */
    public static void getSearchInput(String searchString, int searchType) throws IOException {
        if (searchType == 4) {
            try {
                ((IndexedSearch) searchFactory.search(SearchUtil.getSearchTypeMap().get(SearchUtil.INDEXED_SEARCH))).createIndex(directoryPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (searchType <= 3 && searchType > 0) {
            startSearch(searchString, searchType);
        }
        if (searchType == 0) {
            System.exit(0);
        }
    }

    /**
     * This method gets what search method needs to be invoked based on the search type
     * and starts the search process.
     *
     * @param searchString
     * @param searchType
     * @throws IOException
     */

    private static void startSearch(String searchString, int searchType) throws IOException {
        long startTime = 0;
        long endTime = 0;
        searchMethod = searchFactory.search(SearchUtil.getSearchTypeMap().get(searchType));
        try {
            startTime = Calendar.getInstance().getTime().getTime();
            TreeMap<String, Long> matchCountMap = searchMethod.countMatches(directoryPath, searchString);
            matchCountMap.forEach(((k, v) -> System.out.println(k + ":" + v + " matches")));
            endTime = Calendar.getInstance().getTime().getTime();
            System.out.println("Elapsed time : " + (endTime - startTime) + "ms");
            System.out.println("*************** " + SearchUtil.getSearchTypeMap().get(searchType).toUpperCase() + " ENDED ***************" + "\n");
        } catch (SearchException e) {
            System.out.println(e.getMessage());
        }
    }
}



