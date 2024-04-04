/*
 * File: WordCompletion
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.wordCompletion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCompletion {

    private static WordCompletion instance = null;

    static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    private final TrieNode root;

    WordCompletion() {
        root = new TrieNode();
    }

    public static WordCompletion getInstance() {
        if (instance == null) {
            instance = new WordCompletion();
        }
        return instance;
    }

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    public void insertAll(List<String> words) {
        words.forEach(word -> insert(word.toLowerCase()));
    }

    // Perform word completion given a prefix
    public List<String> wordCompletion(String prefix) {
        List<String> completions = new ArrayList<>();
        TrieNode node = findNode(prefix);
        if (node != null) {
            StringBuilder sb = new StringBuilder(prefix);
            findCompletions(node, sb, completions);
        }
        return completions;
    }

    // Find a node corresponding to the prefix
    private TrieNode findNode(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return null;
            }
            current = current.children.get(ch);
        }
        return current;
    }

    // Find completions recursively starting from a given node
    private void findCompletions(TrieNode node, StringBuilder prefix, List<String> completions) {
        if (node.isEndOfWord) {
            completions.add(prefix.toString());
        }
        for (char ch : node.children.keySet()) {
            prefix.append(ch);
            findCompletions(node.children.get(ch), prefix, completions);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
