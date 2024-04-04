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
            File[] _fls = GoodlifeFolder.listFiles();

            if (_fls != null) {
                for (File _f : _fls) {
                    if (_f.isFile() && _f.getName().endsWith(".html")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean checkHtmlFilesForFit4Less() {
        File GFolder = new File(StringConstants.FIT4LESS_OUTPUT_FOLDER_PATH);

        if (GFolder.exists() && GFolder.isDirectory()) {
            File[] _fs = GFolder.listFiles();

            if (_fs != null) {
                for (File _f : _fs) {
                    if (_f.isFile() && _f.getName().endsWith(".html")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Check if HTML files exist in British folder
    public static boolean checkHtmlFilesForPlanetFitness() {
        File planetFolder = new File(StringConstants.PLANET_FITNESS_OUTPUT_FOLDER_PATH);

        if (planetFolder.exists() && planetFolder.isDirectory()) {
            File[] _fs = planetFolder.listFiles();

            if (_fs != null) {
                for (File _f : _fs) {
                    if (_f.isFile() && _f.getName().endsWith(".html")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static public boolean checkHtmlFiles() {
        return checkHtmlFilesForGoodLife() || checkHtmlFilesForPlanetFitness() || checkHtmlFilesForFit4Less();
    }
}
