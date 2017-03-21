package com.target.search;

import com.target.search.base.SearchInterface;
import com.target.search.exception.SearchException;
import com.target.search.factory.SearchFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nikhil on 3/18/2017.
 */
public class SearchTest {

    private String directoryName = "C:\\sample_text";

    private SearchFactory searchFactory = new SearchFactory();

    // Matches found test for String Search method.
    @Test
    public void stringSearch_matchesFound() throws IOException, SearchException {
        SearchInterface searchBase = searchFactory.search("String Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches(directoryName, "title");
        assertTrue(!matchesMap.isEmpty());
        assertEquals(3,matchesMap.values().size());
        assertEquals(matchesMap.get("C:\\sample_text\\hitchhikers.txt"), Long.valueOf(3));
        assertTrue(matchesMap.keySet().contains("C:\\sample_text\\french_armed_forces.txt"));
    }

    // Matches found test for Regular Expression Search method.
    @Test
    public void regularExpressionSearch_matchesFound() throws IOException, SearchException {
        SearchInterface searchBase = searchFactory.search("Regular Expression Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches(directoryName, ".*rance");
        assertTrue(!matchesMap.isEmpty());
        assertEquals(3,matchesMap.values().size());
        assertEquals(matchesMap.get("C:\\sample_text\\french_armed_forces.txt"), Long.valueOf(18));
        assertTrue(matchesMap.keySet().contains("C:\\sample_text\\hitchhikers.txt"));
    }

    // Matches found test for Index Search method.
    @Test
    public void indexedSearch_matchesFound() throws IOException, SearchException {
        SearchInterface searchBase = searchFactory.search("Indexed Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches(directoryName, "first");
        assertTrue(!matchesMap.isEmpty());
        assertEquals(2,matchesMap.values().size());
        assertEquals(matchesMap.get(matchesMap.firstKey()), Long.valueOf(3));
        assertEquals(matchesMap.get("C:\\sample_text\\hitchhikers.txt"), Long.valueOf(3));
        assertTrue(matchesMap.keySet().contains("C:\\sample_text\\french_armed_forces.txt"));
    }

    // Matches not found test for String Search method.
    @Test
    public void stringSearch_matchesNotFound() throws IOException, SearchException {
        SearchInterface searchBase = searchFactory.search("String Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches(directoryName, "string");
        assertTrue(!matchesMap.isEmpty());
        assertEquals(3,matchesMap.values().size());
    }

    // Matches not found test for Regular Expression Search method.
    @Test
    public void regularExpressionSearch_matchesNotFound() throws IOException, SearchException {
        SearchInterface searchBase = searchFactory.search("Regular Expression Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches(directoryName, "regular");
        assertTrue(!matchesMap.isEmpty());
        assertEquals(3, matchesMap.values().size());
    }

    // Matches found test for Index Search method.
    @Test
    public void indexedSearch_matchesNotFound() throws IOException, SearchException {
        SearchInterface searchBase = searchFactory.search("Indexed Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches(directoryName, "index");
        assertTrue(matchesMap.isEmpty());
        assertEquals(0,matchesMap.values().size());

    }

    // Throw Exception test when there's no directory.
    @Test(expected = SearchException.class)
    public void shouldThrowException() throws SearchException, IOException {
        SearchInterface searchBase = searchFactory.search("String Search");
        TreeMap<String, Long> matchesMap = searchBase.countMatches("C:\\nikhilblue31", "first");
    }
}
