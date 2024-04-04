/*
 * File: FileUtil
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.utils;

import com.acc.fitnessClubAnalysis.constants.StringConstants;

import java.io.File;

public class FileUtil {
    public static boolean checkHtmlFilesForGoodLife() {
        File GoodlifeFolder = new File(StringConstants.GOOD_LIFE_OUTPUT_FOLDER_PATH);

        if (GoodlifeFolder.exists() && GoodlifeFolder.isDirectory()) {
            File[] files = GoodlifeFolder.listFiles();

            // If files array is not null
            if (files != null) {
                // Loop through each file in the array
                for (File file : files) {
                    // Check if file is a regular file and ends with ".html" extension
                    if (file.isFile() && file.getName().endsWith(".html")) {
                        return true; // Have one file
                    }
                }
            }
        }

        return false; // Files not found
    }

    public static boolean checkHtmlFilesForFit4Less() {
        File GFolder = new File(StringConstants.FIT4LESS_OUTPUT_FOLDER_PATH);

        if (GFolder.exists() && GFolder.isDirectory()) {
            File[] files = GFolder.listFiles();

            // If files array is not null
            if (files != null) {
                // Loop through each file in the array
                for (File file : files) {
                    // Check if file is a regular file and ends with ".html" extension
                    if (file.isFile() && file.getName().endsWith(".html")) {
                        return true; // Have one file
                    }
                }
            }
        }

        return false; // Files not found
    }

    // Check if HTML files exist in British folder
    public static boolean checkHtmlFilesForPlanetFitness() {
        File planetFolder = new File(StringConstants.PLANET_FITNESS_OUTPUT_FOLDER_PATH);

        if (planetFolder.exists() && planetFolder.isDirectory()) {
            File[] files = planetFolder.listFiles();

            // If files array is not null
            if (files != null) {
                // Loop through each file in the array
                for (File file : files) {
                    // Check if file is a regular file and ends with ".html" extension
                    if (file.isFile() && file.getName().endsWith(".html")) {
                        return true; // Have one file
                    }
                }
            }
        }

        return false; // No files exist
    }

    // Check if HTML files present in any of the specified folders
    public static boolean checkHtmlFiles() {
        return checkHtmlFilesForGoodLife() || checkHtmlFilesForPlanetFitness() || checkHtmlFilesForFit4Less();
    }
}
