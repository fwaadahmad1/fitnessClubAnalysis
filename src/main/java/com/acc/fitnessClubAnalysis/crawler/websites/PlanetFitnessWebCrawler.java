package com.acc.fitnessClubAnalysis.crawler.websites;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.crawler.BaseWebCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanetFitnessWebCrawler extends BaseWebCrawler {

    public static String _u = "https://www.planetfitness.ca/gyms/";
    static WebDriver _d;
    static WebDriverWait wait;

    public static void scrape(String name) {
        initDriver();
        collectData(name);
        closeDriver();
    }

    public static void initDriver() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);

        _d = new ChromeDriver(options);
        wait = new WebDriverWait(_d, Duration.ofSeconds(30));
        _d.get(_u);
        _d.navigate().refresh();
    }

    public static void collectData(String input) {
        _d.manage().window().maximize();
        WebElement si = _d.findElement(By.id("search"));

        si.sendKeys(input);
        si.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        _d.get("https://www.planetfitness.ca/gyms/?q=" + input);
        String content = _d.getPageSource();
        BaseWebCrawler.createFile(content,
                                  StringConstants.PLANET_FITNESS_OUTPUT_FILE_NAME,
                                  StringConstants.PLANET_FITNESS_OUTPUT_FOLDER_PATH);

        System.out.println("planetFitness data crawled and saved in Json...");
    }

    // close driver
    public static void closeDriver() {
        _d.quit();
    }
}
