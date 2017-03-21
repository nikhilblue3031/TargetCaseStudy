package com.target.search.base.impl;

import com.target.search.base.SearchInterface;
import com.target.search.exception.SearchException;

import java.io.*;
import java.util.TreeMap;

/**
 * Created by nikhil on 3/14/2017.
 */

/**
 * This class implements the SearchInterface and provides the methods
 * to perform the String Search based on the input provided.
 */
public class StringSearch implements SearchInterface {

    /**
     * This method is overrided from the SearchInterface interface and performs the string search.
     *
     * @param directoryPath
     * @param searchString
     * @return
     * @throws SearchException
     * @throws IOException
     */
    @Override
    public TreeMap<String, Long> countMatches(String directoryPath, String searchString) throws SearchException, IOException {
        System.out.println("*************** STRING SEARCH STARTED ***************");
        File[] files = new File(directoryPath).listFiles();
        if (files == null || files.length == 0) {
            throw new SearchException("No files in the directory, could not continue the search process");
        }
        long searchCount = 0;
        String line = "";
        searchString = searchString.toLowerCase();
        TreeMap<String, Long> matches = new TreeMap<String, Long>();

        for (File filePath : files) {
            StringBuilder stringBuilder = new StringBuilder();
            searchCount = 0;
            String fullString = "";
            try (FileInputStream inputStream = new FileInputStream(filePath);) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    fullString = stringBuilder.toString().toLowerCase();
                    int position = fullString.indexOf(searchString);
                    while (position > -1 && position < fullString.length()) {
                        searchCount++;
                        position = position + searchString.length();
                        position = fullString.indexOf(searchString, position);
                    }
                }
            }
            if(searchCount > 0)
            matches.put(filePath.toString(), searchCount);
        }
        return matches;
    }

}
