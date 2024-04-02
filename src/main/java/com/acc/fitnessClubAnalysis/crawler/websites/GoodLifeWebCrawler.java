package com.acc.fitnessClubAnalysis.crawler.websites;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.crawler.BaseWebCrawler;
import com.acc.fitnessClubAnalysis.crawler.interfaces.IWebCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoodLifeWebCrawler extends BaseWebCrawler implements IWebCrawler {
    public static String url = "https://www.goodlifefitness.com/clubs.html#findaclub";
    static WebDriver drvr;
    static WebDriverWait wait;

    public static void scrape(String name) {
        initDriver();
        collectData(name);
        closeDriver();
    }

    // initialize the driver
    public static void initDriver() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);

        drvr = new ChromeDriver(options);

        wait = new WebDriverWait(drvr, Duration.ofSeconds(30));
        drvr.get(url);
        drvr.navigate().refresh();
    }

    public static void collectData(String input) {
        drvr.manage().window().maximize();
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("club-search")));
        searchInput.sendKeys(input);
        searchInput.sendKeys(Keys.ENTER);
        WebElement loader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("js-is-loading")));

        wait.until(ExpectedConditions.invisibilityOf(loader));

        String content = drvr.findElement(By.tagName("html")).getAttribute("outerHTML");

        createFile(url,
                   content,
                   StringConstants.GOOD_LIFE_OUTPUT_FILE_NAME,
                   StringConstants.GOOD_LIFE_OUTPUT_FOLDER_PATH);
        System.out.println("goodLife data extracted and saved in Json...");
    }

    // reset driver
    public static void reset_Driver() {
        drvr.get(url);
    }

    // close driver
    public static void closeDriver() {
        drvr.quit();
    }
}
