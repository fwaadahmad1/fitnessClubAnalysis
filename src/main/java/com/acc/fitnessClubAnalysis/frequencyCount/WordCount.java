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

    private static Map<String, Integer> getWrdFrq(String _s) {
        Map<String, Integer> wc_map = new HashMap<>();
        StringTokenizer _t = new StringTokenizer(_s, " \t\n,.!?");
        if (_t.hasMoreTokens()) {
            do {
                String _w = _t.nextToken().toLowerCase().replaceAll("[^a-zA-Z]", "");
                Integer x = wc_map.getOrDefault(_w, 0);
                if (_w.length() >= 2) {
                    wc_map.put(_w, x + 1);
                } else if (x == null) {
                    wc_map.put(_w, 1);
                }
            } while (_t.hasMoreTokens());
        }
        return wc_map;

    }

    public static Map<String, Integer> countInText(String text) {
        if (text.isBlank()) return new HashMap<>();
        return getWrdFrq(text);
    }
}

