package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * contains information on country of origin
 */
public class ProductionCountry {

    @Nullable
    @SerializedName("name")
    private String name;

    @Nullable
    public String getName() {
        return name;
    }

    public static ProductionCountry buildForTest() {
        ProductionCountry country = new ProductionCountry();
        country.name = "country";
        return country;
    }
}
