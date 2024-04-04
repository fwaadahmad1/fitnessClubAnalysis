/*
 * File: HtmlParser
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.htmlParser;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.crawler.websites.Fit4LessWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.websites.GoodLifeWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.websites.PlanetFitnessWebCrawler;
import com.acc.fitnessClubAnalysis.htmlParser.websites.Fit4lessParser;
import com.acc.fitnessClubAnalysis.htmlParser.websites.GoodLifeParser;
import com.acc.fitnessClubAnalysis.htmlParser.websites.PlanetFitnessParser;
import com.acc.fitnessClubAnalysis.models.Gym;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class HtmlParser {
    public static List<Gym> parseAll() {
        List<Gym> gymList = getGymList();
        saveGymInfo(gymList);
        return gymList;
    }

    private static List<Gym> getGymList() {
        List<Gym> gymList;

        List<Gym> rawList = (new Fit4lessParser()).parseFiles();
        gymList = Fit4LessWebCrawler.crawlAmenities(rawList);

        rawList = (new GoodLifeParser()).parseFiles();
        gymList.addAll(GoodLifeWebCrawler.crawlAmenities(rawList));

        rawList = (new PlanetFitnessParser()).parseFiles();
        gymList.addAll(PlanetFitnessWebCrawler.crawlAmenities(rawList));

        return gymList;
    }

    private static void saveGymInfo(List<Gym> museum_Info_List) {
        // Initialize ObjectMapper for JSON serialization
        ObjectMapper objectMapper = new ObjectMapper();
        // Specify directory path for JSON files
        String directoryPath = StringConstants.FILTERED_DEALS_OUTPUT_FOLDER_PATH;

        try {
            // Create a File object for the JSON directory
            File directory = new File(directoryPath);

            // Create the directory if it doesn't exist
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create a File object for the JSON file
            File file = new File(directory, StringConstants.FILTERED_DEALS_FILE_NAME + ".json");

            // Write museumInfoList to JSON file
            try {
                objectMapper.writeValue(file, museum_Info_List);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
