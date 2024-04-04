package com.acc.fitnessClubAnalysis.crawler.websites;

import com.acc.fitnessClubAnalysis.constants.StringConstants;
import com.acc.fitnessClubAnalysis.crawler.BaseWebCrawler;
import com.acc.fitnessClubAnalysis.models.Gym;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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

        _d = new ChromeDriver(_opts);
        _wt = new WebDriverWait(_d, Duration.ofSeconds(10));
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

    public static List<Gym> crawlAmenities(List<Gym> gymList) {
        List<Gym> gyms = new ArrayList<>();
        initDriver();


        for (Gym gym : gymList) {
            try {

                _d.get("https://www.fit4less.ca/" + gym.get_url());

                List<WebElement> li = _d.findElement(By.className("gym-details-amenities"))
                                        .findElements(By.tagName("li"));
                gym.get_amenities().addAll(li.stream().map(WebElement::getText).toList());
                gyms.add(gym);
            } catch (org.openqa.selenium.NoSuchElementException ignored) {
            }
        }
        closeDriver();
        return gyms;
    }
}
