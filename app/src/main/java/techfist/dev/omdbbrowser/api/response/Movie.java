package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import techfist.dev.omdbbrowser.ApplicationConstant;

/**
 * represents a movie entity, fields are self explanatory
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

    @NonNull
    public String getReleaseDate() {
        return TextUtils.isEmpty(releaseDate) ? ApplicationConstant.NA : releaseDate;
    }

    @NonNull
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

    /**
     * Helper API which generate a static object for purpose of testing
     * @return
     */
    public static Movie buildForTest(){
        Random random = new Random();
        Movie movie = new Movie();
        movie.title = "title";
        movie.originalTitle = "title";
        movie.id = random.nextInt();
        movie.posterUrl = "url";
        movie.releaseDate="213-123-232";
        movie.overView = "adkjgdjasbdsajdbsahjdksa";
        movie.averageRatings = random.nextFloat();
        movie.spokenLanguageList = new ArrayList<>();
        movie.productionCountryList = new ArrayList<>();
        movie.productionCompanyList = new ArrayList<>();
        for(int i = 0 ; i < 5; i++){
            movie.spokenLanguageList.add(SpokenLanguage.buildForTest());
            movie.productionCountryList.add(ProductionCountry.buildForTest());
            movie.productionCompanyList.add(ProductionCompany.buildForTest());
        }
        return movie;
    }

    @Override
    public String toString() {
        return "Movie{" + "totalVote=" + totalVote + ", id=" + id + ", averageRatings=" + averageRatings + ", title='" + title + '\'' +
                ", overView='" + overView + '\'' + ", releaseDate='" + releaseDate + '\'' + ", posterUrl='" + posterUrl + '\'' + '}';
    }
}
