/*
 * File: PageRank
 * Created By: Fwaad Ahmad
 * Created On: 23-03-2024
 */
package com.acc.fitnessClubAnalysis.models;

public class Rank {
    private String name;
    private int ranking;

    public Rank(String name, int ranking) {
        this.name = name;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
