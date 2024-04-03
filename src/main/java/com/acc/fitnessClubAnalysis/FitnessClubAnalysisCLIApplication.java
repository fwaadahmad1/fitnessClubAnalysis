/*
 * File: FitnessClubAnalysisCLIApplication
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis;

import com.acc.fitnessClubAnalysis.crawler.websites.Fit4LessWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.websites.GoodLifeWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.websites.PlanetFitnessWebCrawler;
import com.acc.fitnessClubAnalysis.frequencyCount.WordCount;
import com.acc.fitnessClubAnalysis.htmlParser.HtmlParser;
import com.acc.fitnessClubAnalysis.htmlParser.helpers.HtmlParserHelper;
import com.acc.fitnessClubAnalysis.invertedIndexing.InvertedIndexing;
import com.acc.fitnessClubAnalysis.models.Gym;
import com.acc.fitnessClubAnalysis.models.Rank;
import com.acc.fitnessClubAnalysis.pageRanking.PageRanking;
import com.acc.fitnessClubAnalysis.utils.FileUtil;
import com.acc.fitnessClubAnalysis.validation.InputValidation;

import java.util.*;

public class FitnessClubAnalysisCLIApplication extends InputValidation {

    // Initialize global scanner
    static Scanner scanner = new Scanner(System.in);

    private static final HashMap<String, Integer> cityInputCount = new HashMap<>();
    private static final HashMap<String, Integer> wordSearchCount = new HashMap<>();

    /**
     * Main method to run the program
     */

    public static void main(String[] args) {
        init();
        while (true) {
            // Display menu options
            System.out.println("Select an option:");
            System.out.println("1. Perform Crawling");
            System.out.println("2. Perform Parsing");
            System.out.println("3. Rank Pages");
            System.out.println("4. Create inverted indexing");
            System.out.println("5. Word Frequency Count");
            System.out.println("0. Exit");

            // Read user choice
            int choice = isChoiceValid(scanner.nextLine());

            // Switch based on user choice
            switch (choice) {
                case 1 -> performCrawling();
                case 2, 3, 4, 5 -> handleOtherChoices(choice);
                case 0 -> {
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private static void handleOtherChoices(int choice) {
        if (!htmlFilesAvailable()) return;
        switch (choice) {
            case 2:
                performParsing();
                break;

            case 3:
                PageRanking.rank();
                break;

            case 4:
                InvertedIndexing.indexAll();
                break;

            case 5:
                countFrequency();
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
    }

    private static void displayRecentSearches(HashMap<String, Integer> map) {
        if (!map.isEmpty()) {
            System.out.println("Recent Searches:");
            PriorityQueue<Rank> ranks = new PriorityQueue<>(Comparator.comparingInt(Rank::getRanking).reversed());
            map.forEach((key, value) -> ranks.offer(new Rank(key, value)));
            int i = 0;
            while (!ranks.isEmpty() && i < 3) {
                Rank _rank = ranks.poll();
                System.out.printf("%s: %s\n", _rank.getName(), _rank.getRanking());
                i++;
            }
        }
    }

    private static void countFrequency() {
        displayRecentSearches(wordSearchCount);
        System.out.println("\nEnter 0 to go back");

        String word;
        do {
            System.out.println("Enter any word to search in files:");
            word = scanner.nextLine();
            if (Objects.equals(word, "0")) return;

        } while (!isSpellingCorrect(word));

        int freq = wordSearchCount.getOrDefault(word.toLowerCase(), 0);
        wordSearchCount.put(word.toLowerCase(), freq + 1);

        WordCount.countAll(word);
        System.out.println();
    }

    private static boolean htmlFilesAvailable() {
        if (FileUtil.checkHtmlFiles()) return true;
        System.out.println("No Files available.\nPlease perform Crawling first.\n");
        return false;
    }

    private static void performParsing() {
        List<Gym> gymList = HtmlParser.parseAll();
        HtmlParserHelper.filterDeals(gymList);
    }

    private static void performCrawling() {
        displayRecentSearches(cityInputCount);
        System.out.println("\nEnter 0 to go back");
        String cityName;
        do {
            System.out.println("Enter any city name in Ontario:");
            cityName = scanner.nextLine();
            if (Objects.equals(cityName, "0")) return;

        } while (!isCityNameValid(cityName.toLowerCase()));


        int freq = cityInputCount.getOrDefault(cityName.toLowerCase(), 0);
        cityInputCount.put(cityName.toLowerCase(), freq + 1);

        scrapeAll(cityName.substring(0, 1)
                          .toUpperCase() + cityName.substring(1)); // To perform web crawl from 3 websites
    }

    // Perform web crawling from both websites
    private static void scrapeAll(String name) {
        PlanetFitnessWebCrawler.scrape(name);
        Fit4LessWebCrawler.scrape(name);
        GoodLifeWebCrawler.scrape(name);
    }
}
