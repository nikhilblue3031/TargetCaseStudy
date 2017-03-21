package com.target.search.main;


import com.target.search.exception.SearchException;
import com.target.search.util.SearchStarter;
import com.target.search.util.SearchUtil;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by nikhil on 3/14/2017.
 */

public class SearchMain {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SearchException, IOException {

        System.out.println("Enter the string you want to search:");

        String searchString = scanner.nextLine();

        System.out.println("*************** Available inputs ***************");

        SearchUtil.getSearchTypeMap().forEach(((k, v) -> System.out.println(k + ":" + v)));

        int searchType;

        do {
            System.out.println("*************** Please select a number from 0 - 4 ***************");

            searchType = scanner.nextInt();
            //Calls the method which gets search type and start the search process based on the input provided.
            SearchStarter.getSearchInput(searchString, searchType);
        }
        while (true);
    }
}
