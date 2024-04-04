/*
 * File: WordCount
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.frequencyCount;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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

    public static Map<String, Integer> countInText(String text) {
        return getWordFrequency(text);
    }
}

