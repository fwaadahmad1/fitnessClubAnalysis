/*
 * File: InvertedIndexing
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.invertedIndexing;


import com.acc.fitnessClubAnalysis.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class InvertedIndexing {

    // Function to build an inverted index
    public static Map<String, Set<String>> buildInvertedIndex(List<File> documents) {
        Map<String, Set<String>> invertedIndex = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String document = getFileContent(documents.get(i));
            // Tokenize text by splitting on non-alphanumeric characters
            String[] tokens = document.toLowerCase().split("[^a-zA-Z0-9]+");
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    Set<String> docUrls = invertedIndex.getOrDefault(token, new HashSet<>());
                    docUrls.add("Document " + i); // Storing document location as an example
                    invertedIndex.put(token, docUrls);
                }
            }
        }
        return invertedIndex;
    }

    private static String getFileContent(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }


    // Function to print the inverted index
    public static void printInvertedIndex(Map<String, Set<String>> invertedIndex) {
        System.out.println("Inverted Index:");
        for (Map.Entry<String, Set<String>> entry : invertedIndex.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void indexAll() {

        List<File> documents = FileUtil.getAllHtmlFiles();
        // Build inverted index
        Map<String, Set<String>> invertedIndex = buildInvertedIndex(documents);

        // Print the inverted index
        printInvertedIndex(invertedIndex);

    }
}

