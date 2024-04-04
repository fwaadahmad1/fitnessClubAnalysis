/*
 * File: HtmlParserHelper
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.htmlParser.helpers;

import com.acc.fitnessClubAnalysis.models.Gym;

import java.util.List;

public class HtmlParserHelper {

    // Display filtered deals from parsing
    public static void filterDeals(List<Gym> museum_List) {
        // Display header for filtered deals
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
        System.out.printf("*%100s %80s%n", "Crawled data from 3 websites", "*");
        System.out.println(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");

        // Display the list of museum data
        displayList(museum_List);
    }

    // Display the list of museum data
    public static void displayList(List<Gym> museum_List) {
        // Display header for museum data
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-40s | %-60s | %-15s| %-30s| %-25s|%n",
                          "Name",
                          "Address",
                          "Membership type",
                          "Provider",
                          "Price");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        // Loop through each museum object in the list
        for (Gym museum : museum_List) {
            // Display museum information
            System.out.printf("|%-40s | %-60s | %-15s| %-30s| %-25s|%n",
                              museum.getName(),
                              museum.getAddress(),
                              museum.getMembershipName(),
                              museum.getProvider(),
                              museum.getPrice());
        }

        // Display footer for museum data
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
