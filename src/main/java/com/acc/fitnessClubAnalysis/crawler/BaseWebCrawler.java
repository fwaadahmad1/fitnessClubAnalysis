package com.acc.fitnessClubAnalysis.crawler;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseWebCrawler {

    protected static ChromeOptions options = new ChromeOptions();
    protected static WebDriverWait wait;


    static {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    public static void writeContent(String folderName, String content, String fileName, String fileExtension) {
        try {
            File fr = new File(folderName);
            File frf = new File(folderName + fileName + fileExtension);
            if (!fr.exists()) {

                boolean created = fr.mkdirs();
                if (created) {

                    FileWriter _flwr = new FileWriter(frf, false);
                    _flwr.write(content);
                    _flwr.close();
                } else {
                    System.out.println("Folder creation failed.");
                }
            } else {

                FileWriter _flwr = new FileWriter(frf, false);
                _flwr.write(content);
                _flwr.close();
            }
        } catch (Exception e) {
            System.out.println("File Creation error!");
        }
    }

    public static void createFile(String content, String fileName, String folder) {
        writeContent(folder, content, fileName, ".html");
    }
}
