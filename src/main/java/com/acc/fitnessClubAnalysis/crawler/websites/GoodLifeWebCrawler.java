package com.acc.fitnessClubAnalysis.crawler.websites;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.crawler.BaseWebCrawler;
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

public class GoodLifeWebCrawler extends BaseWebCrawler {
    public static String _u = "https://www.goodlifefitness.com/clubs.html#findaclub";
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
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("club-search")));
        searchInput.sendKeys(input);
        searchInput.sendKeys(Keys.ENTER);
        WebElement loader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("js-is-loading")));

        wait.until(ExpectedConditions.invisibilityOf(loader));

        String content = _d.findElement(By.tagName("html")).getAttribute("outerHTML");

        createFile(content, StringConstants.GOOD_LIFE_OUTPUT_FILE_NAME, StringConstants.GOOD_LIFE_OUTPUT_FOLDER_PATH);
        System.out.println("goodLife data crawled and saved in Json...");
    }

    public static void closeDriver() {
        _d.quit();
    }
}
