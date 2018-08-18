package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * contains information of spoken languages
 */
public class SpokenLanguage {

    @Nullable
    @SerializedName("name")
    private String name;

    @Nullable
    public String getName() {
        return name;
    }

    public static SpokenLanguage buildForTest() {
        SpokenLanguage language = new SpokenLanguage();
        language.name = "langauge";
        return language;
    }
}
