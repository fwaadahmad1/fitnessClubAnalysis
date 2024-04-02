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

import static com.acc.fitnessClubAnalysis.constants.StringConstants.PLANET_FITNESS_OUTPUT_FOLDER_PATH;

public class PlanetFitnessParser implements IHtmlParser {
    public List<Gym> parseFiles() {
        // creating a list
        List<Gym> gymList = new ArrayList<>();
        // creating folders

        File folder = new File(PLANET_FITNESS_OUTPUT_FOLDER_PATH);


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

        String provider = "planetfitness gym";
        // Parse local HTML file
        if (file_Path.toLowerCase().contains("planet")) {
            provider = "planet";
        }

        try {
            File inputt = new File(file_Path);


            Document doc = Jsoup.parse(inputt, "UTF-8");
            Elements divs = doc.select("div.flex.flex-1.flex-col.justify-between");

            for (Element div : divs) {


                // Get the gym address
                Element locationElement = div.selectFirst("b.text-lg");
                String location = null;
                if (locationElement != null) {
                    location = locationElement.text();
                }
                // System.out.println("Location: " + location);
                Element addressElement = div.selectFirst("span.text-gray-dark");
                String address = null;
                if (addressElement != null) {
                    address = addressElement.text();
                }
                // System.out.println("Address: " + address);

                Gym g1 = new Gym(location, address, "pf black card", provider, "29.99 per month");
                Gym g2 = new Gym(location, address, "classic", provider, "15 per month");

                Info_List.add(g1);
                Info_List.add(g2);


                System.out.println(); // Add a newline for clarity between each div
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return Info_List;
    }

}