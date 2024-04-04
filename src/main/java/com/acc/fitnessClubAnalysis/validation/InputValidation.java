/*
 * File: InputValidation
 * Created By: Fwaad Ahmad
 * Created On: 31-03-2024
 */
package com.acc.fitnessClubAnalysis.validation;

import com.acc.fitnessClubAnalysis.constants.Cities;
import com.acc.fitnessClubAnalysis.dictionary.SpellCheckDict;
import com.acc.fitnessClubAnalysis.wordCompletion.CompletionDictionary;

import java.util.List;

import static com.acc.fitnessClubAnalysis.utils.StringUtil.capitalize;

public class InputValidation {
    private static final SpellCheckDict dictionary = SpellCheckDict.getInstance();
    private static final CompletionDictionary COMPLETION_DICTIONARY = CompletionDictionary.getInstance();

    public InputValidation() {
        init();
    }

    public static void init() {
        initDictionary();
    }

    private static void initDictionary() {
        COMPLETION_DICTIONARY.insertAll(Cities.ONTARIO_CITIES_LIST);
        dictionary.putAll(Cities.ONTARIO_CITIES_LIST);
    }

    public static int isChoiceValid(String choice) {
        return choice.trim().matches("^[0-9]$") ? Integer.parseInt(choice.trim()) : -1;
    }

    public static boolean isYNChoiceValid(String choice) {
        return choice.trim().matches("[ynYN]");
    }

    public static boolean isSpellingCorrect(String word) {
        return dictionary._acm(word.toLowerCase());
    }

    public static boolean isCityNameValid(String cityName) {
        List<String> cities = COMPLETION_DICTIONARY.wordCompletion(cityName);
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
