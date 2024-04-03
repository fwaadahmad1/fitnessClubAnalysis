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

    public static String url = "https://www.planetfitness.ca/gyms/";
    static WebDriver driver;
    static WebDriverWait wait;

    public static void scrape(String name) {
        initDriver();
        collectData(name);
        closeDriver();
    }

    // initialize the driver
    public static void initDriver() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(url);
        driver.navigate().refresh();
    }

    public static void collectData(String input) {
        driver.manage().window().maximize();
        WebElement searchInput = driver.findElement(By.id("search"));

        // Type text into the input field
        searchInput.sendKeys(input);
        searchInput.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.get("https://www.planetfitness.ca/gyms/?q=" + input);
        String content = driver.getPageSource();
        BaseWebCrawler.createFile(url,
                                  content,
                                  StringConstants.PLANET_FITNESS_OUTPUT_FILE_NAME,
                                  StringConstants.PLANET_FITNESS_OUTPUT_FOLDER_PATH);

        System.out.println("planetFitness data extracted and saved in Json...");
    }

    // reset driver
    public static void reset_Driver() {
        driver.get(url);
    }

    // close driver
    public static void closeDriver() {
        driver.quit();
    }
}
