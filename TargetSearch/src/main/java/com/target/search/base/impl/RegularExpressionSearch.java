package com.target.search.base.impl;

import com.target.search.base.SearchInterface;
import com.target.search.exception.SearchException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nikhil on 3/14/2017.
 */

/**
 * This class implements the SearchInterface and provides the methods
 * to perform the Regular Expression Search based on the input provided.
 */
public class RegularExpressionSearch implements SearchInterface {

    /**
     * This method is overrided from the SearchInterface interface and performs the regular expression search.
     *
     * @param directoryPath
     * @param searchString
     * @return
     * @throws SearchException
     * @throws IOException
     */
    @Override
    public TreeMap<String, Long> countMatches(String directoryPath, String searchString) throws SearchException, IOException {
        System.out.println("*************** REGULAR EXPRESSION SEARCH STARTED ***************");
        File[] files = new File(directoryPath).listFiles();
        if (files == null || files.length == 0) {
            throw new SearchException("No files in the directory, could not continue the search process");
        }
        long searchCount = 0;
        String line = "";
        TreeMap<String, Long> matches = new TreeMap<String, Long>();
        searchString = searchString.toLowerCase();
        Pattern searchPattern = Pattern.compile(searchString);
        for (File filePath : files) {
            searchCount = 0;
            try (FileInputStream inputStream = new FileInputStream(filePath);) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
                    while ((line = bufferedReader.readLine()) != null) {
                        List<String> words = Arrays.asList(line.split(" "));
                        for (String word : words) {
                            Matcher matcher = searchPattern.matcher(word.toLowerCase());
                            while (matcher.find()) {
                                searchCount++;
                            }
                        }
                    }
                }
            }
            if(searchCount > 0)
            matches.put(filePath.toString(), searchCount);
        }
        return matches;
    }
}
