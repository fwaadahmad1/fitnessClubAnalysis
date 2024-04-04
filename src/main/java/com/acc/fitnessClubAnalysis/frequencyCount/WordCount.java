/*
 * File: WordCount
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.frequencyCount;

import com.acc.fitnessClubAnalysis.utils.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.acc.fitnessClubAnalysis.pageRanking.PageRanking.extractMetaContent;
import static com.acc.fitnessClubAnalysis.pageRanking.PageRanking.extractText;

public class WordCount {

    // Using algorithm to find word frequency using hashMap.
    private static Map<String, Integer> getWordFrequency(String s) {
        Map<String, Integer> word_Count = new HashMap<>();
        StringTokenizer token = new StringTokenizer(s, " \t\n\r\f,.!?");
        while (token.hasMoreTokens()) {

            String word = token.nextToken().toLowerCase().replaceAll("[^a-zA-Z]", "");
            Integer i = word_Count.getOrDefault(word, 0);
            if (word.length() >= 2) {
                word_Count.put(word, i + 1);
            } else if (i == null) {
                word_Count.put(word, 1);
            }
        }
        return word_Count;

    }

    //	Calculating the lexicalRichness of the word i.e. ratio of unique words to the total number of words
    private static double calculateLexicalRichness(Map<String, Integer> word_Frequency) {
        int unique_Count = word_Frequency.size();
        int total_Count = word_Frequency.values().stream().mapToInt(Integer::intValue).sum();
        return (double) unique_Count / total_Count;
    }

    public static void countAll(String word) {
        System.out.println("\n Word: " + word);
        FileUtil.getAllHtmlFiles().forEach(file -> System.out.printf("\n%s: %s", file.getName(), count(word, file)));
    }

    public static long count(String word, File file) {
        String s;
        try {
            Document doc = Jsoup.parse(file, "UTF-8");

            String metaContent = extractMetaContent(doc);
            s = metaContent + " " + extractText(doc);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return 0;
        }
        Map<String, Integer> word_Frequency = getWordFrequency(s);

        return word_Frequency.get(word);
    }

    public static Map<String, Integer> countInFile(File file) {
        String s;
        try {
            Document doc = Jsoup.parse(file, "UTF-8");

            String metaContent = extractMetaContent(doc);
            s = metaContent + " " + extractText(doc);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new HashMap<>();
        }

        return getWordFrequency(s);
    }

    public static Map<String, Integer> countInText(String text) {
        return getWordFrequency(text);
    }

    public static long count(String word, String content) {
        Map<String, Integer> word_Frequency = getWordFrequency(content);
        return word_Frequency.get(word);
    }
}

