/*
 * File: HtmlParser
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.htmlParser;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.htmlParser.interfaces.IHtmlParser;
import com.acc.fitnessClubAnalysis.htmlParser.websites.Fit4lessParser;
import com.acc.fitnessClubAnalysis.htmlParser.websites.GoodLifeParser;
import com.acc.fitnessClubAnalysis.htmlParser.websites.PlanetFitnessParser;
import com.acc.fitnessClubAnalysis.models.Gym;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    public static List<Gym> parseAll() {
        List<Gym> gymList = getGymList();
        saveGymInfo(gymList);
        return gymList;
    }

    private static List<Gym> getGymList() {
        List<Gym> gymList = new ArrayList<>();
//        List<IHtmlParser> parsers = List.of(new GoodLifeParser());
        List<IHtmlParser> parsers = List.of(new Fit4lessParser(), new GoodLifeParser(), new PlanetFitnessParser());
        parsers.forEach(iHtmlParser -> gymList.addAll(iHtmlParser.parseFiles()));

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
