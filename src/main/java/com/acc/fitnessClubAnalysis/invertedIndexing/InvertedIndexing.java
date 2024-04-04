/*
 * File: InvertedIndexing
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.invertedIndexing;


import com.acc.fitnessClubAnalysis.frequencyCount.WordCount;
import com.acc.fitnessClubAnalysis.models.Gym;

import java.util.*;

public class InvertedIndexing {

    private static InvertedIndexing invertedIndexing;

    public static InvertedIndexing getInstance() {
        if (invertedIndexing == null) {
            invertedIndexing = new InvertedIndexing();
        }
        return invertedIndexing;
    }

    private final Map<String, Set<Integer>> indexMap = new HashMap<>();

    public Map<String, Set<Integer>> getIndexMap() {
        return indexMap;
    }

    // Function to build an inverted index
    public void buildInvertedIndex(List<Gym> gyms) {
        for (int i = 0; i < gyms.size(); i++) {
            Gym gym = gyms.get(i);
            for (Map.Entry<String, Integer> entry : WordCount.countInText(gym.toString()).entrySet()) {
                Set<Integer> wordSet = indexMap.getOrDefault(entry.getKey(), new HashSet<>());
                wordSet.add(i);
                indexMap.put(entry.getKey(), wordSet);
            }
        }

//        indexMap.forEach(((s, integers) -> System.out.println(s + " : [" + String.join(", ", integers.stream().map(String::valueOf).toList()) + "]")));
    }
}

