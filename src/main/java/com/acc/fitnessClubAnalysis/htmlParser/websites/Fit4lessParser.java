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

import static com.acc.fitnessClubAnalysis.constants.StringConstants.FIT4LESS_OUTPUT_FOLDER_PATH;

public class Fit4lessParser implements IHtmlParser {
    public List<Gym> parseFiles() {
        // creating a list
        List<Gym> gymList = new ArrayList<>();

        File folder = new File(FIT4LESS_OUTPUT_FOLDER_PATH);


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

        String provider = "fit4less";

        try {
            File input = new File(file_Path);


            Document doc = Jsoup.parse(input, "UTF-8");
            Elements gymDivs = doc.select("div.find-gym__result");

            // Loop through each div and extract its contents
            for (Element gymDiv : gymDivs) {

                // Get the gym address
                Element titleElement = gymDiv.selectFirst("p.find-gym__result--title a");
                String gymName = null;
                if (titleElement != null) {
                    gymName = titleElement.text();
                }

                Element addressElement = gymDiv.selectFirst("p.find-gym__result--address");
                String address = null;
                if (addressElement != null) {
                    address = addressElement.text();
                }

                Gym g1 = new Gym(gymName, address, "black card", provider, "13.99 per 2 weeks", 13.99 / 2);
                Gym g2 = new Gym(gymName, address, "4less card", provider, "7.99 per 2 weeks", 7.99 / 2);

                Info_List.add(g1);
                Info_List.add(g2);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Info_List;
    }

}
