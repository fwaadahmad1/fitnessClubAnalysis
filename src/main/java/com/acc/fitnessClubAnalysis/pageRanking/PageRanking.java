/*
 * File: PageRanking
 * Created By: Fwaad Ahmad
 * Created On: 23-03-2024
 */
package com.acc.fitnessClubAnalysis.pageRanking;

import com.acc.fitnessClubAnalysis.models.Gym;
import com.acc.fitnessClubAnalysis.models.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PageRanking {

    public static void rank(List<Gym> gymList) {
        displayPageRanking(calculatePageRanking(gymList));
    }

    private static PriorityQueue<Rank> calculatePageRanking(List<Gym> gymList) {
        PriorityQueue<Rank> pageRank = new PriorityQueue<>(Comparator.comparingInt(Rank::getRanking).reversed());
        pageRank.offer(new Rank("Fit4Less",
                                (int) gymList.stream().filter(o -> o.getProvider().equals("fit4less")).count()));
        pageRank.offer(new Rank("GoodLife",
                                (int) gymList.stream()
                                             .filter(o -> o.getProvider().equals("GoodLife fitness gym"))
                                             .count()));
        pageRank.offer(new Rank("Planet Fitness",
                                (int) gymList.stream()
                                             .filter(o -> o.getProvider().equals("planetfitness gym"))
                                             .count()));
        return pageRank;
    }

    private static void displayPageRanking(PriorityQueue<Rank> pageRanking) {
        System.out.println("Web Ranking based on number of gyms at location:");
        int i = 1;
        while (!pageRanking.isEmpty()) {
            Rank page = pageRanking.poll();
            System.out.println(i + ". " + page.getName());
            i++;
        }
    }
}

