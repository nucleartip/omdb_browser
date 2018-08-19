package techfist.dev.omdbbrowser.data.dao;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import techfist.dev.omdbbrowser.api.response.ProductionCompany;
import techfist.dev.omdbbrowser.api.response.ProductionCountry;
import techfist.dev.omdbbrowser.api.response.SpokenLanguage;

/**
 * Converts take cares of converting data from list to string, and from string to list
 * its used along with room database.
 *
 * ALL API's are self explanatory
 */
public class Converters {

    @TypeConverter
    public static List<ProductionCompany> productionCompanyFromString(String value) {
        Type listType = new TypeToken<ArrayList<ProductionCompany>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static List<ProductionCountry> productionCountryFromString(String value) {
        Type listType = new TypeToken<ArrayList<ProductionCountry>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static List<SpokenLanguage> spokenLanguageFromString(String value) {
        Type listType = new TypeToken<ArrayList<SpokenLanguage>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String productionCompanyJson(List<ProductionCompany> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static String productionCountryJson(List<ProductionCountry> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static String spokenLanguageJson(List<SpokenLanguage> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
