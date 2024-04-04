/*
 * File: WordCompletion
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.wordCompletion;

import java.util.*;

public class CompletionDictionary {

    private static CompletionDictionary completionDictionaryInstance = null;

    static class TNode {
        Map<Character, TNode> _c;
        boolean _is_end;

        TNode() {
            this._c = new HashMap<>();
            this._is_end = false;
        }
    }

    private final TNode root;

    CompletionDictionary() {
        root = new TNode();
    }

    public static CompletionDictionary getInstance() {
        if (completionDictionaryInstance == null) {
            completionDictionaryInstance = new CompletionDictionary();
        }
        return completionDictionaryInstance;
    }

    // Insert a word into the Trie
    public void _insrt_wrd(String _w) {
        TNode _c = root;
        char[] charArray = _w.toCharArray();
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char _ch = charArray[i];
            _c._c.putIfAbsent(_ch, new TNode());
            _c = _c._c.get(_ch);
        }
        _c._is_end = true;
    }

    public void insertAll(List<String> words) {
        words.forEach(word -> _insrt_wrd(word.toLowerCase()));
    }

    // Perform word completion given a prefix
    public List<String> wordCompletion(String prefix) {
        List<String> completions = new ArrayList<>();
        TNode node = _fn(prefix);
        if (node != null) {
            StringBuilder sb = new StringBuilder(prefix);
            findCompletions(node, sb, completions);
        }
        return completions;
    }

    // Find a node corresponding to the prefix
    private TNode _fn(String _p) {
        TNode _c = root;
        for (char ch : _p.toCharArray()) {
            if (!_c._c.containsKey(ch)) {
                return null;
            }
            _c = _c._c.get(ch);
        }
        return _c;
    }

    // Find completions recursively starting from a given node
    private void findCompletions(TNode _n, StringBuilder _p, List<String> _cs) {
        if (_n._is_end) {
            _cs.add(_p.toString());
        }
        for (Iterator<Character> iterator = _n._c.keySet().iterator(); iterator.hasNext(); ) {
            char ch = iterator.next();
            _p.append(ch);
            findCompletions(_n._c.get(ch), _p, _cs);
            _p.deleteCharAt(_p.length() - 1);
        }
    }
}
