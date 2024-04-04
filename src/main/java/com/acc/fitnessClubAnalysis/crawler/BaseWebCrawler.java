package com.acc.fitnessClubAnalysis.crawler;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WebCrawler class for finding hyperlinks and writing content to files.
 */
public abstract class BaseWebCrawler {

    protected static ChromeOptions options = new ChromeOptions();
    protected static WebDriverWait wait;


    static {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    /**
     * Writes content to a file in the specified folder.
     *
     * @param folderName    Name of the folder
     * @param content       Content to be written
     * @param fileName      Name of the file
     * @param fileExtension File extension (e.g., ".html")
     */
    public static void writeContent(String folderName, String content, String fileName, String fileExtension) {
        try {
            File check_Folder = new File(folderName);
            File ff = new File(folderName + fileName + fileExtension);
            if (!check_Folder.exists()) {

                boolean created = check_Folder.mkdirs();
                if (created) {

                    FileWriter fiWriter = new FileWriter(ff, false);
                    fiWriter.write(content);
                    fiWriter.close();
                } else {
                    System.out.println("Failed to create the folder.");
                }
            } else {

                FileWriter ffWriter = new FileWriter(ff, false);
                ffWriter.write(content);
                ffWriter.close();
            }
        } catch (Exception e) {
            System.out.println("Error occurring in file");
        }
    }

    /**
     * Creates a file, writes content to it, and returns a Hashtable containing file information.
     *
     * @param url      URL associated with the file
     * @param content  Content to be written
     * @param fileName Name of the file
     * @param folder   Folder in which the file is created
     * @return Hashtable containing file information
     */
    public static Hashtable<String, String> createFile(String url, String content, String fileName, String folder) {
        Hashtable<String, String> mapOfURL = new Hashtable<>();
        mapOfURL.put(fileName + ".html", url);
        writeContent(folder, content, fileName, ".html");
        return mapOfURL;
    }
}
