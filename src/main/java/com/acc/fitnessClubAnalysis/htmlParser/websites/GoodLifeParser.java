package com.acc.fitnessClubAnalysis.htmlParser.websites;


import com.acc.fitnessClubAnalysis.htmlParser.interfaces.IHtmlParser;
import com.acc.fitnessClubAnalysis.models.Gym;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.acc.fitnessClubAnalysis.constants.StringConstants.GOOD_LIFE_OUTPUT_FOLDER_PATH;

// html parser for both avis and budget parser
public class GoodLifeParser implements IHtmlParser {
    public List<Gym> parseFiles() {
        // creating a list
        List<Gym> gymList = new ArrayList<>();

        File folder = new File(GOOD_LIFE_OUTPUT_FOLDER_PATH);


        if (folder.isDirectory()) {

            File[] files = folder.listFiles();

            if (files != null) {
                for (File _f : files) {

                    // saving all data to file
                    gymList.addAll(parseWebsite(_f.getAbsolutePath()));
                }
            } else {
                System.out.println("The folder is empty.");
            }
        }

        return gymList;
    }

    private List<Gym> parseWebsite(String file_Path) {
        // creating arraylist
        List<Gym> Info_List = new ArrayList<>();

        String provider = "GoodLife fitness gym";
        try {
            File input = new File(file_Path);
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements clubDivs = doc.select("div.c-card__wrapper");

            // Loop through each div and extract its contents
            for (Element clubDiv : clubDivs) {

                // Get the club address
                Element nameElement = clubDiv.selectFirst("h3.c-card__title");
                String clubName = null;
                if (nameElement != null) {
                    clubName = nameElement.text();
                }

                String address = "";
                String phone = "";
                try {
                    Elements addressElement = clubDiv.select("p.c-card__contact");
                    address = addressElement.text().split("\\(")[0].trim();
                    Pattern pattern = Pattern.compile("\\(\\d{3}\\) \\d{3}-\\d{4}");
                    Matcher matcher = pattern.matcher(addressElement.text());
                    while (matcher.find()) {
                        phone = matcher.group();
                    }
                } catch (Exception ignored) {
                }

                String url;
                try {
                    Element urlElement = clubDiv.selectFirst("a.c-card__title-link");
                    url = urlElement.attributes().get("href");
                } catch (Exception e) {
                    url = "";
                }

                Gym g1 = new Gym(clubName,
                                 url,
                                 address,
                                 phone,
                                 "Premium",
                                 provider,
                                 "35.99 per 2 weeks",
                                 35.99 / 2,
                                 new ArrayList<>());
                Gym g2 = new Gym(clubName,
                                 url,
                                 address,
                                 phone,
                                 "Ultimate",
                                 provider,
                                 "39.99 per 2 weeks",
                                 39.99 / 2,
                                 new ArrayList<>());
                Gym g3 = new Gym(clubName,
                                 url,
                                 address,
                                 phone,
                                 "Performance",
                                 provider,
                                 "54.99 per 2 weeks",
                                 54.99 / 2,
                                 new ArrayList<>());
                Info_List.add(g1);
                Info_List.add(g2);
                Info_List.add(g3);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Info_List;
    }


}
