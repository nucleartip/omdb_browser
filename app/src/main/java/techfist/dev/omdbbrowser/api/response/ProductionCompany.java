package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * contains information on production company details
 */
public class ProductionCompany {

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("origin_country")
    @Nullable
    private String originCountry;

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getOriginCountry() {
        return originCountry;
    }

    public static ProductionCompany buildForTest() {
        ProductionCompany company = new ProductionCompany();
        company.name = "name";
        company.originCountry = "origin";
        return company;
    }
}
