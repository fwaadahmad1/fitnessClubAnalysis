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

    public static String url = "https://www.fit4less.ca/locations";

    static WebDriver _d;

    public static void scrape(String name) {
        initDriver();
        try {
            collectData(name);
        } catch (Exception ignored) {
        }
        closeDriver();
    }
    public static void initDriver() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);

        _d = new ChromeDriver(options);
        wait = new WebDriverWait(_d, Duration.ofSeconds(10));
        _d.get(url);
        _d.navigate().refresh();
    }

    public static void collectData(String name) {
        _d.manage().window().maximize();
        _d.findElement(By.id("province-dropdown")).click();

        WebElement _dropdown = _d.findElement(By.cssSelector("#province-dropdown > ul > li[data-provname=\"Ontario\"]"));
        Actions _acs = new Actions(_d);
        _acs.scrollToElement(_dropdown).perform();
        _dropdown.click();

        _d.findElement(By.id("city-dropdown")).click();

        String cssSelector = String.format("#city-dropdown > ul > li[data-cityname=\"%s\"]", name);

        WebElement _city = _d.findElement(By.cssSelector(cssSelector));

        _acs.scrollToElement(_city).perform();
        _city.click();
        _d.findElement(By.xpath("//*[@id=\"btn-find-your-gym\"]")).click();

        String cont = _d.getPageSource();
        createFile(cont, StringConstants.FIT4LESS_OUTPUT_FILE_NAME, StringConstants.FIT4LESS_OUTPUT_FOLDER_PATH);
        System.out.println("fit4less data crawled and saved in Json...");
    }
    
    public static void closeDriver() {
        _d.quit();
    }
}
