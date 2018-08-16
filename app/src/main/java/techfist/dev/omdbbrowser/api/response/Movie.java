package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.EmptyStackException;
import java.util.List;

import techfist.dev.omdbbrowser.ApplicationConstant;

/**
 * represents a movie entity
 */
public class Movie {

    @SerializedName("vote_count")
    private int totalVote;

    @SerializedName("id")
    private long id;

    @SerializedName("vote_average")
    private float averageRatings;

    @SerializedName("title")
    @Nullable
    private String title;

    @SerializedName("original_title")
    @Nullable
    private String originalTitle;

    @SerializedName("overview")
    @Nullable
    private String overView;

    @SerializedName("release_date")
    @Nullable
    private String releaseDate;

    @SerializedName("backdrop_path")
    @Nullable
    private String posterUrl;

    @SerializedName("production_companies")
    @Nullable
    private List<ProductionCompany> productionCompanyList;

    @SerializedName("production_countries")
    @Nullable
    private List<ProductionCountry> productionCountryList;

    @SerializedName("spoken_languages")
    @Nullable
    private List<SpokenLanguage> spokenLanguageList;

    public int getTotalVote() {
        return totalVote;
    }

    public long getId() {
        return id;
    }

    public float getAverageRatings() {
        return averageRatings;
    }

    @NonNull
    public String getTitle() {
        return TextUtils.isEmpty(title) ? ApplicationConstant.EMPTY : title;
    }

    @NonNull
    public String getOriginalTitle(){
        return TextUtils.isEmpty(originalTitle) ? ApplicationConstant.EMPTY : originalTitle;
    }

    @NonNull
    public String getOverView() {
        return TextUtils.isEmpty(overView) ? ApplicationConstant.EMPTY : overView;
    }

    public String getReleaseDate() {
        return TextUtils.isEmpty(releaseDate) ? ApplicationConstant.NA : releaseDate;
    }

    public String getPosterUrl() {
        return TextUtils.isEmpty(posterUrl) ? ApplicationConstant.EMPTY : posterUrl;
    }

    @Nullable
    public List<ProductionCompany> getProductionCompanyList() {
        return productionCompanyList;
    }

    @Nullable
    public List<ProductionCountry> getProductionCountryList() {
        return productionCountryList;
    }

    @Nullable
    public List<SpokenLanguage> getSpokenLanguageList() {
        return spokenLanguageList;
    }

    @Override
    public String toString() {
        return "Movie{" + "totalVote=" + totalVote + ", id=" + id + ", averageRatings=" + averageRatings + ", title='" + title + '\'' +
                ", overView='" + overView + '\'' + ", releaseDate='" + releaseDate + '\'' + ", posterUrl='" + posterUrl + '\'' + '}';
    }

    public static class ProductionCompany{
        @SerializedName("name")
        private String name;

        @SerializedName("origin_country")
        private String originCountry;

        public String getName() {
            return name;
        }

        public String getOriginCountry() {
            return originCountry;
        }
    }


    public static class ProductionCountry{
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class SpokenLanguage{
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
