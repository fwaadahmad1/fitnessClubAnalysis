/*
 * File: InputValidation
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.validation;

import com.acc.fitnessClubAnalysis.constants.Cities;
import com.acc.fitnessClubAnalysis.dictionary.WordDictionary;
import com.acc.fitnessClubAnalysis.wordCompletion.WordCompletion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.acc.fitnessClubAnalysis.utils.StringUtil.capitalize;

public class InputValidation {
    private static final WordDictionary dictionary = WordDictionary.getInstance();
    private static final WordCompletion wordCompletion = WordCompletion.getInstance();

    public InputValidation() {
        init();
    }

    public static void init() {
        initDictionary();
    }

    private static void initDictionary() {
        wordCompletion.insertAll(Cities.ONTARIO_CITIES_LIST);
        dictionary.putAll(Cities.ONTARIO_CITIES_LIST);
        try (BufferedReader br = new BufferedReader(new FileReader(
                "src/main/java/com/acc/fitnessClubAnalysis/dictionary/words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.inst(line.trim());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static int isChoiceValid(String choice) {
        return choice.trim().matches("^[0-5]$") ? Integer.parseInt(choice.trim()) : -1;
    }

    public static boolean isSpellingCorrect(String word) {
        return dictionary.autoCorrectMethod(word.toLowerCase());
    }

    public static boolean isCityNameValid(String cityName) {
        List<String> cities = wordCompletion.wordCompletion(cityName);
        switch (cities.size()) {
            case 0:
                System.out.println("City not in Ontario");
                isSpellingCorrect(cityName);
                return false;
            case 1:
                if (cities.getFirst().equals(cityName)) return true;
            default:
                System.out.println("Incorrect city name. Suggested cities: ");
                cities.forEach(city -> System.out.println(capitalize(city)));
        }
        return false;
    }


}
