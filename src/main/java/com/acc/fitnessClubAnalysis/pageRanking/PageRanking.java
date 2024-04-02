/*
 * File: PageRanking
 * Created By: Fwaad Ahmad
 * Created On: 23-03-2024
 */
package com.acc.fitnessClubAnalysis.pageRanking;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.models.Rank;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PageRanking {

    public static void rank() {
        List<String> folder_Paths = List.of(StringConstants.FIT4LESS_OUTPUT_FOLDER_PATH,
                                            StringConstants.GOOD_LIFE_OUTPUT_FOLDER_PATH,
                                            StringConstants.PLANET_FITNESS_OUTPUT_FOLDER_PATH);

        List<String> filePaths = getFilePaths(folder_Paths);

        // Search keywords
        String[] keywords = {"gym", "gyms", "fitness"};

        // Calculate ranking
        PriorityQueue<Rank> pageRankings = calculatePageRankings(filePaths, keywords);

        // Display rankings
        System.out.println();
        System.out.println("Website : \t\t Rank");
        while (!pageRankings.isEmpty()) {
            Rank pageRank = pageRankings.poll();
            System.out.printf("%s: \t\t %s\n", pageRank.getName(), pageRank.getRanking());
        }
        System.out.println();
    }

    private static List<String> getFilePaths(List<String> folderPaths) {
        return folderPaths.stream()
                          .map(File::new)
                          .map(File::listFiles)
                          .filter(Objects::nonNull)
                          .flatMap(Arrays::stream)
                          .map(File::getAbsolutePath)
                          .collect(Collectors.toList());
    }

    public static PriorityQueue<Rank> calculatePageRankings(List<String> filePaths, String[] keywords) {
        PriorityQueue<Rank> pageRankings = new PriorityQueue<>(Comparator.comparingInt(Rank::getRanking)
                                                                         .reversed());

        filePaths.forEach(filePath -> {
            try {
                File file = new File(filePath);
                Document doc = Jsoup.parse(file, "UTF-8");

                String metaContent = extractMetaContent(doc);
                String content = metaContent + " " + extractText(doc);

                int ranking = calculatePageRanking(content, keywords);
                pageRankings.offer(new Rank(file.getName().replaceAll("Deals.html", ""), ranking));
            } catch (IOException e) {
                System.err.println("Error reading file: " + filePath);
            }
        });

        return pageRankings;
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

    public static int calculatePageRanking(String content, String[] keywords) {
        int ranking = 0;
        String lowerContent = content.toLowerCase();

        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(keyword.toLowerCase()) + "\\b");
            Matcher matcher = pattern.matcher(lowerContent);
            int keywordOccurrences = 0;
            while (matcher.find()) {
                keywordOccurrences++;
            }
            ranking += keywordOccurrences;
        }

        return ranking;
    }
}

