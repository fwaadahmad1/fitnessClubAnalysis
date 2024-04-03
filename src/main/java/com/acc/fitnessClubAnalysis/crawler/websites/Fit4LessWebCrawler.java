package com.acc.fitnessClubAnalysis.crawler.websites;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.crawler.BaseWebCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fit4LessWebCrawler extends BaseWebCrawler {

    // URL for web crawling
    public static String url = "https://www.fit4less.ca/locations";

    static WebDriver drvr;

    public static void scrape(String name) {
        initDriver();
        try {
            collectData(name);
        } catch (Exception ignored) {
        }
        closeDriver();
    }

    /**
     * Initialize the WebDriver
     */
    public static void initDriver() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);

        drvr = new ChromeDriver(options);
        wait = new WebDriverWait(drvr, Duration.ofSeconds(10));
        drvr.get(url);
        drvr.navigate().refresh();
    }

    /**
     * Collects data from the web page
     */
    public static void collectData(String name) {
        // Maximize the window
        drvr.manage().window().maximize();
        // Get web element
        drvr.findElement(By.id("province-dropdown")).click();

        WebElement _dropdown = drvr.findElement(By.cssSelector("#province-dropdown > ul > li[data-provname=\"Ontario\"]"));
        Actions actions = new Actions(drvr);
        actions.scrollToElement(_dropdown).perform();
        _dropdown.click();

        drvr.findElement(By.id("city-dropdown")).click();

        // Construct the CSS selector with the city name variable
        String cssSelector = String.format("#city-dropdown > ul > li[data-cityname=\"%s\"]", name);

        // Find the city element using the constructed CSS selector
        WebElement _city = drvr.findElement(By.cssSelector(cssSelector));

        actions.scrollToElement(_city).perform();
        _city.click();
        drvr.findElement(By.xpath("//*[@id=\"btn-find-your-gym\"]")).click();

        String cont = drvr.getPageSource();
        // Saving data to file
        createFile(url, cont, StringConstants.FIT4LESS_OUTPUT_FILE_NAME, StringConstants.FIT4LESS_OUTPUT_FOLDER_PATH);
        // Printing the data
        System.out.println("fit4less data extracted and saved in Json...");
    }

    /**
     * Reinitialize the web driver by loading the British Museum URL
     */
    public static void reset_Driver() {
        drvr.get(url);
    }

    /**
     * Quit the WebDriver and close all associated windows
     */
    public static void closeDriver() {
        drvr.quit();
    }
}
