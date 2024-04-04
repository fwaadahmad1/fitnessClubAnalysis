/*
 * File: Dictionary
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.dictionary;


import com.acc.fitnessClubAnalysis.models.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class WordDictionary {

    private static WordDictionary dictionary = null;

    static class WordNode {    //  harryNod represents the nodes of the Splay Tree //
        String ky;
        int value;
        WordNode left, right;

        WordNode(String ky) {    // here each node contains a key(ky) and an integer value //
            this.ky = ky;
            this.value = 1;
            left = right = null;
        }
    }

    private WordNode root;

    private WordDictionary() {
        root = null;
    }

    public static WordDictionary getInstance() {
        if (dictionary == null) dictionary = new WordDictionary();
        return dictionary;
    }

    //  method for the right rotation //
    private WordNode rotate_r(WordNode x) {
        WordNode y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    // method for the left rotation //
    private WordNode rotate_l(WordNode x) {
        WordNode y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    // Splaying operation being performed to bring the key(ky) to root of the tree//
    private WordNode splay(WordNode root, String ky) {
        if (root == null || root.ky.equalsIgnoreCase(ky)) return root;

        if (root.ky.compareToIgnoreCase(ky) > 0) {
            if (root.left == null) return root;
            // Zig-Zig rotation (Left Left)
            if (root.left.ky.compareToIgnoreCase(ky) > 0) {
                root.left.left = splay(root.left.left, ky);
                root = rotate_r(root);
            }
            // Zig-Zag rotation (Left Right)
            else if (root.left.ky.compareToIgnoreCase(ky) < 0) {
                root.left.right = splay(root.left.right, ky);
                if (root.left.right != null) root.left = rotate_l(root.left);
            }
            if (root.left == null) return root;
            return rotate_r(root);
        } else {
            if (root.right == null) return root;
            // Zag-Zag rotation (Right Right)
            if (root.right.ky.compareToIgnoreCase(ky) < 0) {
                root.right.right = splay(root.right.right, ky);
                root = rotate_l(root);
            }
            // Zag-Zig rotation (Right Left)
            else if (root.right.ky.compareToIgnoreCase(ky) > 0) {
                root.right.left = splay(root.right.left, ky);
                if (root.right.left != null) root.right = rotate_r(root.right);
            }
            if (root.right == null) return root;
            return rotate_l(root);
        }
    }

    // method for inserting the words //
    public void inst(String ky) {
        root = instRec(root, ky);
    }

    private WordNode instRec(WordNode root, String ky) {
        if (root == null) return new WordNode(ky);

        // Splaying the harryNod to the root if found
        root = splay(root, ky);

        int camp = ky.compareToIgnoreCase(root.ky);
        // increase the frequency of the key which is already present //
        if (camp == 0) {
            root.value++;
            return root;
        }

        WordNode newharryNod = new WordNode(ky);
        // If key is less than root's key, make it left child //
        if (camp < 0) {
            newharryNod.right = root;
            newharryNod.left = root.left;
            root.left = null;
        }
        // If key is greater than root's key, make it right child //
        else {
            newharryNod.left = root;
            newharryNod.right = root.right;
            root.right = null;
        }
        return newharryNod;
    }

    // method for deleting the words //
    public void dlt(String ky) {
        root = dltRec(root, ky);
    }

    private WordNode dltRec(WordNode root, String ky) {
        if (root == null) return null;

        root = splay(root, ky);

        int camp = ky.compareToIgnoreCase(root.ky);

        // If the key is not found //
        if (camp != 0) return root;

        // decreasing the frequency of the key which is present more than once //
        if (root.value > 1) {
            root.value--;
            return root;
        }

        // if key frequency is 1 //
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;

        //joining subtrees//
        WordNode tp = root;
        root = splay(root.left, ky);
        root.right = tp.right;
        return root;
    }

    // method for searching //
    public boolean srch(String ky) {
        root = splay(root, ky);
        return root != null && root.ky.equalsIgnoreCase(ky);
    }

    //  Checks if the dictionary is empty.
    public boolean isEmpty() {
        return root == null;
    }

    // performing inorder traversal to list all the words //
    public void traversal_inodr() {
        traversal_inodrRec(root);
    }

    private void traversal_inodrRec(WordNode root) {
        if (root != null) {
            traversal_inodrRec(root.left);
            System.out.println(root.ky + " (" + root.value + ")");
            traversal_inodrRec(root.right);
        }
    }

    // method of Spell check using similarity metrics //
    public String spl_chk(String wd) {
        if (!srch(wd)) {
            System.out.println("Word not found. Did you mean: ");
            suggestWords(this.root, wd, 3);
        }
        return "";
    }

    // method for suggesting similar words using Levenshtein distance algorithm //
    private void suggestWords(WordNode root, String wd, int threshold) {
        PriorityQueue<Rank> wordRanking = new PriorityQueue<>(Comparator.comparingInt(Rank::getRanking));
        sgst_words(root, wd, threshold, wordRanking);

        int WORD_LIMIT = 5;
        int i = 0;
        while (!wordRanking.isEmpty() && i < WORD_LIMIT) {
            Rank wordRank = wordRanking.poll();
            System.out.println(wordRank.getName());
            i++;
        }
    }

    private void sgst_words(WordNode root, String wd, int threshold, PriorityQueue<Rank> pq) {
        if (root != null) {
            // Calculate Edit distance between targetWord and currentNode.element
            int dt = levenshteindt(wd, root.ky);

            if (dt <= threshold) {
//                System.out.println(root.ky);
                pq.offer(new Rank(root.ky, dt));
            }

            sgst_words(root.left, wd, threshold, pq);
            sgst_words(root.right, wd, threshold, pq);
        }
    }

    //  method for Auto-correct with similarity metrics.//
    public boolean autoCorrectMethod(String w) {
        if (isEmpty()) {
            System.out.println("Dictionary is empty.");
            return false;
        }

        root = splay(root, w);

        if (root.ky.compareTo(w) != 0) {
            // Word not found, suggest corrections
            spl_chk(w);
            return false;
        } else return true;
    }


    // Levenshtein distance algorithm to calculate the similarity between two words //
    private int levenshteindt(String wd1, String wd2) {

        int m = wd1.length();
        int n = wd2.length();

        int[][] dynamic = new int[m + 1][n + 1];

        for (int k = 0; k <= m; k++) {
            dynamic[k][0] = k;
        }

        for (int p = 0; p <= n; p++) {
            dynamic[0][p] = p;
        }

        for (int k = 1; k <= m; k++) {
            for (int p = 1; p <= n; p++) {
                if (wd1.charAt(k - 1) == wd2.charAt(p - 1)) {
                    dynamic[k][p] = dynamic[k - 1][p - 1];
                } else {
                    dynamic[k][p] = 1 + Math.min(dynamic[k - 1][p - 1], Math.min(dynamic[k][p - 1], dynamic[k - 1][p]));
                }
            }
        }

        return dynamic[m][n];
    }

    public void putAll(List<String> words) {
        words.forEach(word -> inst(word.toLowerCase()));
    }
}

