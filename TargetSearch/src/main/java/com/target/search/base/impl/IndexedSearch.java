package com.target.search.base.impl;

import com.target.search.base.IndexInterface;
import com.target.search.base.SearchInterface;
import com.target.search.exception.SearchException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by nikhil on 3/14/2017.
 */

/**
 * This class implements the SearchInterface and IndexInterface and provides the methods
 * to perform the Indexed Search based on the input provided.
 */
public class IndexedSearch implements SearchInterface, IndexInterface {

    private static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
    private static File indexPath = new File("C:\\targetIndexes");
    long searchCount = 0;

    /**
     * This method opens the File system directory where indexes are created and searches for the
     * search string provided.
     *
     * @param searchString
     * @param matches
     * @throws Exception
     */
    private static void searchIndex(String searchString, TreeMap<String, Long> matches) throws Exception {
        System.out.println("*************** INDEXED SEARCH STARTED ***************");
        try (Directory directory = FSDirectory.open(indexPath);) {
            try (IndexReader indexReader = DirectoryReader.open(directory);) {
                QueryParser queryParser = new QueryParser(Version.LUCENE_47, "contents", analyzer);
                Query query = queryParser.parse(searchString);
                IndexSearcher indexSearcher = new IndexSearcher(indexReader);
                int hitsPerPage = 1000;
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                indexSearcher.search(query, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;
                for (int i = 0; i < hits.length; ++i) {
                    int docId = hits[i].doc;
                    Document d = indexSearcher.doc(docId);
                    String path = d.get("path");
                    if (matches.containsKey(path)) {
                        long count = matches.get(path);
                        matches.put(path, count + 1);
                    } else {
                        matches.put(path, 1L);
                    }
                }
            }
        }
    }

    /**
     * This method is overrided from the SearchInterface Interface and invokes the index search process.
     *
     * @param directoryPath
     * @param searchString
     * @return
     * @throws SearchException
     */
    @Override
    public TreeMap<String, Long> countMatches(String directoryPath, String searchString) throws SearchException {
        TreeMap<String, Long> matches = new TreeMap<String, Long>();
        try {
            searchIndex(searchString, matches);
        } catch (Exception e) {
            throw new SearchException("Error occurred in the index searching", e);
        }
        return matches;
    }

    /**
     * This method is overrided from the Index Interface ,creates the index directory
     * and creates the indexes for the files in the directory.
     *
     * @param directoryPath
     * @throws SearchException
     * @throws IOException
     */
    @Override
    public void createIndex(String directoryPath) throws SearchException, IOException {
        System.out.println("*************** CREATING or UPDATING INDEX ***************");

        File[] files = new File(directoryPath).listFiles();
        if (files == null || files.length == 0) {
            throw new SearchException("No files in the directory, could not continue the indexing process");
        }

        Directory directory = FSDirectory.open(indexPath);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
        try (IndexWriter indexWriter = new IndexWriter(directory, config);) {
            indexWriter.deleteAll();
            for (File filePath : files) {
                try (FileInputStream inputStream = new FileInputStream(filePath);) {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            StringTokenizer stringTokenizer = new StringTokenizer(line);
                            while (stringTokenizer.hasMoreElements()) {
                                Document doc = new Document();
                                doc.add(new TextField("path", filePath.getCanonicalPath(), Field.Store.YES));
                                doc.add(new TextField("contents", stringTokenizer.nextToken().toString(), Field.Store.YES));
                                indexWriter.addDocument(doc);
                            }
                        }
                    }
                }
            }
            System.out.println("*************** INDEXING DONE ***************");
        }
    }

}