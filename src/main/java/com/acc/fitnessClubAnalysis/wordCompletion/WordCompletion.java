/*
 * File: WordCompletion
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.wordCompletion;

import java.util.*;

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

    // Collect all words stored in the trie
    public List<String> collectWords() {
        List<String> words = new ArrayList<>();
        collectWordsFromNode(root, new StringBuilder(), words);
        return words;
    }

    // Helper method for collecting words recursively
    private void collectWordsFromNode(TrieNode node, StringBuilder prefix, List<String> words) {
        if (node.isEndOfWord) {
            words.add(prefix.toString());
        }
        for (char ch : node.children.keySet()) {
            prefix.append(ch);
            collectWordsFromNode(node.children.get(ch), prefix, words);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WordCompletion trie = new WordCompletion();

        while (true) {
            System.out.println("\nWord Completion Menu:");
            System.out.println("1. Add word");
            System.out.println("2. Word completion");
            System.out.println("3. Print dictionary");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter a word to add to dictionary: ");
                    String wordToAdd = scanner.nextLine().toLowerCase();
                    trie.insert(wordToAdd);
                    System.out.println("Word added to dictionary.");
                    break;
                case 2:
                    System.out.print("Enter a prefix to find completions: ");
                    String prefix = scanner.nextLine().toLowerCase();
                    List<String> completions = trie.wordCompletion(prefix);
                    if (!completions.isEmpty()) {
                        System.out.println("Completions for prefix '" + prefix + "':");
                        for (String completion : completions) {
                            System.out.println(completion);
                        }
                    } else {
                        System.out.println("No completions found for prefix '" + prefix + "'.");
                    }
                    break;
                case 3:
                    System.out.println("Printing dictionary:");
                    List<String> dictionary = trie.collectWords();
                    if (!dictionary.isEmpty()) {
                        System.out.println("Words in dictionary:");
                        for (String word : dictionary) {
                            System.out.println(word);
                        }
                    } else {
                        System.out.println("Dictionary is empty.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
