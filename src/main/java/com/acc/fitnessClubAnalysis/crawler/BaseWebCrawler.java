package com.acc.fitnessClubAnalysis.crawler;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseWebCrawler {

    protected static ChromeOptions _opts = new ChromeOptions();
    protected static WebDriverWait _wt;


    static {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    public static void writeContentToFile(File frf, String _c) {
        try {
            FileWriter _flwr = new FileWriter(frf, false);
            _flwr.write(_c);
            _flwr.close();
        } catch (IOException e) {
            System.err.println("Error while writing content to file: " + e.getMessage());
        }
    }

    public static void createFile(String content, String fileName, String folder) {
        File fr = new File(folder);
        File frf = new File(folder + fileName + ".html");
        if (!fr.exists()) {

            boolean created = fr.mkdirs();
            if (created) {
                writeContentToFile(frf, content);
            } else {
                System.out.println("Folder creation failed.");
            }
        } else {
            writeContentToFile(frf, content);
        }
    }
}
