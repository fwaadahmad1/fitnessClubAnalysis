/*
 * File: FitnessClubAnalysisCLIApplication
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class FitnessClubAnalysisCLIApplication extends InputValidation {

    private static final HashMap<String, Integer> cityInputCount = new HashMap<>();
    // Initialize global scanner
    static Scanner scanner = new Scanner(System.in);
    private static List<Gym> gymList = new ArrayList<>();

    /**
     * Main method to run the program
     */

    public static void main(String[] args) {
        init();
        performCrawling();
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
        outerLoop:
        while (true) {
            String crawlingChoice;
            do {
                System.out.println("Do you want to perform crawling? (y/n):");
                crawlingChoice = scanner.nextLine();
                if (isYNChoiceValid(crawlingChoice)) break;
                System.out.println("Invalid choice!");
            } while (true);

            if (crawlingChoice.equals("y") || crawlingChoice.equals("Y")) {
                displayRecentSearches(cityInputCount);
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

            if (htmlFilesAvailable()) {
                if (crawlingChoice.equals("y") || crawlingChoice.equals("Y")) performParsing();
                else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {

                        // Read JSON file and convert to array of Java objects
                        Gym[] file_gyms = objectMapper.readValue(new File(StringConstants.FILTERED_DEALS_OUTPUT_FOLDER_PATH + StringConstants.FILTERED_DEALS_FILE_NAME + ".json"),
                                                                 Gym[].class);
                        gymList.addAll(Arrays.stream(file_gyms).toList());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    InvertedIndexing.getInstance().buildInvertedIndex(gymList);
                }
                innerLoop:
                while (true) {
                    System.out.println("Select an option:");
                    System.out.println("1. Show All");
                    System.out.println("2. Filter by brand");
                    System.out.println("3. Filter by Amenity");
                    System.out.println("9. Go Back");
                    System.out.println("0. Exit");
                    int innerChoice = isChoiceValid(scanner.nextLine());
                    switch (innerChoice) {
                        case 1 -> printList(gymList, true);
                        case 2 -> {
                            boolean flag = true;
                            String brand = "";
                            while (true) {
                                System.out.println("0: Go Back");
                                System.out.println("Chose a brand:");
                                System.out.println("1. Fit4Less");
                                System.out.println("2. GoodLifeFitness");
                                System.out.println("3. PlanetFitness");
                                int brandChoice = isChoiceValid(scanner.nextLine());
                                if (brandChoice == 0) {
                                    flag = false;
                                    break;
                                }
                                brand = switch (brandChoice) {
                                    case 1 -> "fitless";
                                    case 2 -> "goodlife";
                                    case 3 -> "planetfitness";
                                    default -> "";
                                };
                                if (!brand.isBlank()) break;
                                System.out.println("Invalid choice!");
                            }
                            if (flag) {
                                Set<Integer> filteredIndex = InvertedIndexing.getInstance()
                                                                             .getIndexMap()
                                                                             .getOrDefault(brand, new HashSet<>());
                                List<Gym> filteredGymList = new ArrayList<>();
                                filteredIndex.forEach(idx -> filteredGymList.add(gymList.get(idx)));
                                printList(filteredGymList, false);
                            }
                        }

                        case 3 -> {
                            boolean flag = true;
                            String brand = "";
                            while (true) {
                                System.out.println("0: Go Back");
                                System.out.println("Chose a brand:");
                                System.out.println("1. Parking");
                                System.out.println("2. Wifi");
                                System.out.println("3. Co-Ed");
                                System.out.println("4. Massage");
                                System.out.println("5. Locker");
                                System.out.println("6. Showers");
                                System.out.println("7. Sauna");
                                int brandChoice = isChoiceValid(scanner.nextLine());
                                if (brandChoice == 0) {
                                    flag = false;
                                    break;
                                }
                                brand = switch (brandChoice) {
                                    case 1 -> "parking";
                                    case 2 -> "wifi";
                                    case 3 -> "coed";
                                    case 4 -> "massage";
                                    case 5 -> "locker";
                                    case 6 -> "showers";
                                    case 7 -> "sauna";
                                    default -> "";
                                };
                                if (!brand.isBlank()) break;
                                System.out.println("Invalid choice!");
                            }
                            if (flag) {

                                Set<Integer> filteredIndex = InvertedIndexing.getInstance()
                                                                             .getIndexMap()
                                                                             .getOrDefault(brand, new HashSet<>());
                                List<Gym> filteredGymList = new ArrayList<>();
                                filteredIndex.forEach(idx -> filteredGymList.add(gymList.get(idx)));
                                printList(filteredGymList, true);
                            }
                        }
                        case 9 -> {
                            break innerLoop;
                        }

                        case 0 -> {
                            break outerLoop;
                        }
                        default -> System.out.println("Invalid choice. Please select again.");
                    }
                }
            } else {
                performCrawling();
            }
        }
    }

    // Perform web crawling from both websites
    private static void scrapeAll(String name) {
        PlanetFitnessWebCrawler.scrape(name);
        Fit4LessWebCrawler.scrape(name);
        GoodLifeWebCrawler.scrape(name);
    }
}
