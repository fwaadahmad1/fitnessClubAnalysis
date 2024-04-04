/*
 * File: PageRanking
 * Created By: Fwaad Ahmad
 * Created On: 23-03-2024
 */
package com.acc.fitnessClubAnalysis.pageRanking;

import com.acc.fitnessClubAnalysis.models.Gym;
import com.acc.fitnessClubAnalysis.models.Rank;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PageRanking {

    public static void rank(List<Gym> gymList) {
        displayPageRanking(calculatePageRanking(gymList));
    }

    public static String extractMetaContent(Document doc) {
        StringBuilder metaContent = new StringBuilder();
        Elements metaTags = doc.select("meta");

        for (Element metaTag : metaTags) {
            metaContent.append(metaTag.attr("content")).append(" ");
        }

        return metaContent.toString();
    }

    public static String extractText(Document doc) {
        StringBuilder textContent = new StringBuilder();
        Elements elements = doc.select("p, h1, h2, h3, h4, h5, h6, li, div, span, title");

        for (Element element : elements) {
            textContent.append(element.text()).append(" ");
        }

        return textContent.toString();
    }

    public static PriorityQueue<Rank> calculatePageRanking(List<Gym> gymList) {
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

    public static void displayPageRanking(PriorityQueue<Rank> pageRanking) {
        System.out.println("Web Ranking based on number of gyms at location:");
        while (!pageRanking.isEmpty()) {
            Rank page = pageRanking.poll();
            System.out.println(page.getName() + ": " + page.getRanking());
        }
    }
}

