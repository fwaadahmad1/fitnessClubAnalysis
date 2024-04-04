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

public class SpellCheckDict {

    private static SpellCheckDict _d = null;

    static class WordNode {
        String _k;
        int _v;
        WordNode _l, _r;

        WordNode(String _k) {    // here each node contains a key(ky) and an integer value //
            this._k = _k;
            this._v = 1;
            _l = _r = null;
        }
    }

    private WordNode _r;

    private SpellCheckDict() {
        _r = null;
    }

    public static SpellCheckDict getInstance() {
        if (_d == null) _d = new SpellCheckDict();
        return _d;
    }

    private WordNode rr(WordNode x) {
        WordNode y = x._l;
        x._l = y._r;
        y._r = x;
        return y;
    }

    private WordNode rl(WordNode x) {
        WordNode y = x._r;
        x._r = y._l;
        y._l = x;
        return y;
    }

    private WordNode _sply(WordNode _r, String _k) {
        if (_r == null || _r._k.equalsIgnoreCase(_k)) return _r;

        if (_r._k.compareToIgnoreCase(_k) > 0) {
            if (_r._l == null) return _r;
            if (_r._l._k.compareToIgnoreCase(_k) > 0) {
                _r._l._l = _sply(_r._l._l, _k);
                _r = rr(_r);
            } else if (_r._l._k.compareToIgnoreCase(_k) < 0) {
                _r._l._r = _sply(_r._l._r, _k);
                if (_r._l._r != null) _r._l = rl(_r._l);
            }
            if (_r._l == null) return _r;
            return rr(_r);
        } else {
            if (_r._r == null) return _r;
            if (_r._r._k.compareToIgnoreCase(_k) < 0) {
                _r._r._r = _sply(_r._r._r, _k);
                _r = rl(_r);
            } else if (_r._r._k.compareToIgnoreCase(_k) > 0) {
                _r._r._l = _sply(_r._r._l, _k);
                if (_r._r._l != null) _r._r = rr(_r._r);
            }
            if (_r._r == null) return _r;
            return rl(_r);
        }
    }

    public void _i(String _k) {
        _r = _ir(_r, _k);
    }

    private WordNode _ir(WordNode _r, String _k) {
        if (_r == null) return new WordNode(_k);

        // Splaying the harryNod to the root if found
        _r = _sply(_r, _k);

        int _cp = _k.compareToIgnoreCase(_r._k);
        // increase the frequency of the key which is already present //
        if (_cp == 0) {
            _r._v++;
            return _r;
        }

        WordNode _nn = new WordNode(_k);
        // If key is less than root's key, make it left child //
        if (_cp < 0) {
            _nn._r = _r;
            _nn._l = _r._l;
            _r._l = null;
        }
        // If key is greater than root's key, make it right child //
        else {
            _nn._l = _r;
            _nn._r = _r._r;
            _r._r = null;
        }
        return _nn;
    }

    public boolean _s(String ky) {
        _r = _sply(_r, ky);
        return _r != null && _r._k.equalsIgnoreCase(ky);
    }

    public boolean _ie() {
        return _r == null;
    }

    public String chk_sl(String _w) {
        if (!_s(_w)) {
            _sw(this._r, _w, 3);
        }
        return "";
    }

    // method for suggesting similar words using Levenshtein distance algorithm //
    private void _sw(WordNode root, String wd, int threshold) {
        PriorityQueue<Rank> wordRanking = new PriorityQueue<>(Comparator.comparingInt(Rank::getRanking));
        __sw(root, wd, threshold, wordRanking);

        if (!wordRanking.isEmpty()) System.out.println("Word not found. Did you mean: ");
        int WORD_LIMIT = 5;
        int i = 0;
        while (!wordRanking.isEmpty() && i < WORD_LIMIT) {
            Rank wordRank = wordRanking.poll();
            System.out.println(wordRank.getName());
            i++;
        }
    }

    private void __sw(WordNode _r, String _w, int _t, PriorityQueue<Rank> _pq) {
        if (_r != null) {
            // Calculate Edit distance between targetWord and currentNode.element
            int dt = _ldt(_w, _r._k);

            if (dt <= _t) {
//                System.out.println(root.ky);
                _pq.offer(new Rank(_r._k, dt));
            }

            __sw(_r._l, _w, _t, _pq);
            __sw(_r._r, _w, _t, _pq);
        }
    }

    public boolean _acm(String _w) {
        if (_ie()) {
            System.out.println("Empty Dictionary.");
            return false;
        }

        _r = _sply(_r, _w);

        if (_r._k.compareTo(_w) != 0) {
            // Word not found, suggest corrections
            chk_sl(_w);
            return false;
        } else return true;
    }


    private int _ldt(String _w1, String _w2) {

        int m = _w1.length();
        int n = _w2.length();

        int[][] _d = new int[m + 1][n + 1];

        for (int x = 0; x <= m; x++) {
            _d[x][0] = x;
        }

        for (int y = 0; y <= n; y++) {
            _d[0][y] = y;
        }

        for (int l = 1; l <= m; l++) {
            for (int p = 1; p <= n; p++) {
                if (_w1.charAt(l - 1) == _w2.charAt(p - 1)) {
                    _d[l][p] = _d[l - 1][p - 1];
                } else {
                    _d[l][p] = 1 + Math.min(_d[l - 1][p - 1], Math.min(_d[l][p - 1], _d[l - 1][p]));
                }
            }
        }

        return _d[m][n];
    }

    public void putAll(List<String> words) {
        words.forEach(word -> _i(word.toLowerCase()));
    }
}

