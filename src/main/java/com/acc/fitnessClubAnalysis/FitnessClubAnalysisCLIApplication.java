/*
 * File: FitnessClubAnalysisCLIApplication
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis;

import com.acc.fitnessClubAnalysis.crawler.websites.Fit4LessWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.websites.GoodLifeWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.websites.PlanetFitnessWebCrawler;
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

    private static List<Gym> gymList = new ArrayList<>();

    /**
     * Main method to run the program
     */

    public static void main(String[] args) {
        init();

        outerLoop:
        while (true) {
            // Display menu options
            System.out.println("Select an option:");
            System.out.println("1. Perform Crawling");
            System.out.println("2. Perform Parsing");
            System.out.println("0. Exit");

            // Read user choice
            int choice = isChoiceValid(scanner.nextLine());

            // Switch based on user choice
            switch (choice) {
                case 1 -> performCrawling();
                case 2 -> {
                    if (htmlFilesAvailable()) {
                        performParsing();
                        innerLoop:
                        while (true) {
                            System.out.println("Select an option:");
                            System.out.println("1. Show All");
                            System.out.println("2. Filter by brand");
                            System.out.println("0. Go Back");
                            int innerChoice = isChoiceValid(scanner.nextLine());
                            switch (innerChoice) {
                                case 1 -> printList(gymList, true);
                                case 2 -> {
                                    String brand;
                                    while (true) {
                                        System.out.println("0: Go Back");
                                        System.out.println("Chose a brand:");
                                        System.out.println("1. Fit4Less");
                                        System.out.println("2. GoodLifeFitness");
                                        System.out.println("3. PlanetFitness");
                                        int brandChoice = isChoiceValid(scanner.nextLine());
                                        if (brandChoice == 0) break innerLoop;
                                        brand = switch (brandChoice) {
                                            case 1 -> "fitless";
                                            case 2 -> "goodlife";
                                            case 3 -> "planetfitness";
                                            default -> "";
                                        };
                                        if (!brand.isBlank()) break;
                                        System.out.println("Invalid choice!");
                                    }
                                    Set<Integer> filteredIndex = InvertedIndexing.getInstance()
                                                                                 .getIndexMap()
                                                                                 .getOrDefault(brand, new HashSet<>());
                                    List<Gym> filteredGymList = new ArrayList<>();
                                    filteredIndex.forEach(idx -> filteredGymList.add(gymList.get(idx)));
                                    printList(filteredGymList, false);
                                }
                                case 0 -> {
                                    break innerLoop;
                                }
                                default -> System.out.println("Invalid choice. Please select again.");
                            }
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    private static void printList(List<Gym> listToPrint, boolean showRank) {
        System.out.println("Do you want to sort by price: y/n");

        String innerChoice = scanner.nextLine();

        if (isYNChoiceValid(innerChoice)) {
            switch (innerChoice) {
                case "y", "Y" -> {
                    sortingLoop:
                    while (true) {
                        System.out.println("\n0: Go back");
                        System.out.println("Sorting Type: ");
                        System.out.println("1: Ascending");
                        System.out.println("2: Descending ");
                        int sortingChoice = isChoiceValid(scanner.nextLine());
                        switch (sortingChoice) {
                            case 0 -> {
                                break sortingLoop;
                            }
                            case 1 -> {
                                listToPrint.sort(Comparator.comparingDouble(Gym::get_effectivePrice));
                                if (showRank) PageRanking.rank(listToPrint);
                                HtmlParserHelper.displayList(listToPrint);
                                break sortingLoop;
                            }
                            case 2 -> {
                                listToPrint.sort(Comparator.comparingDouble(Gym::get_effectivePrice).reversed());
                                if (showRank) PageRanking.rank(listToPrint);
                                HtmlParserHelper.displayList(listToPrint);
                                break sortingLoop;
                            }
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }
                case "n", "N" -> {
                    if (showRank) PageRanking.rank(listToPrint);
                    HtmlParserHelper.displayList(listToPrint);
                }
            }
        } else {
            System.out.println("Invalid Choice!!");
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

    private static boolean htmlFilesAvailable() {
        if (FileUtil.checkHtmlFiles()) return true;
        System.out.println("No Files available.\nPlease perform Crawling first.\n");
        return false;
    }

    private static void performParsing() {
        gymList = HtmlParser.parseAll();
        InvertedIndexing.getInstance().buildInvertedIndex(gymList);
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
