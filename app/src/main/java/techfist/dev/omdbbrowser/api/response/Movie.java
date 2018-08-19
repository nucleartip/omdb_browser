package techfist.dev.omdbbrowser.api.response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import techfist.dev.omdbbrowser.ApplicationConstant;
import techfist.dev.omdbbrowser.data.dao.Converters;


/**
 * represents a movie entity, fields are self explanatory
 *
 * same POJO is used by database for persistence and by retrofit for api json response conversion
 * you can read mpre about room database by android here.
 *
 * https://developer.android.com/training/data-storage/room/
 */
@Entity(indices = {@Index("id"),
        @Index(value = {"title"})}, primaryKeys = {"id"})
public class Movie {

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int totalVote;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private float averageRatings;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Nullable private String title;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    @Nullable private String originalTitle;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    @Nullable private String overView;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Nullable private String releaseDate;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    @Nullable private String posterUrl;

    @ColumnInfo(name = "production_companies")
    @SerializedName("production_companies")
    @TypeConverters(Converters.class)
    @Nullable
    private List<ProductionCompany> productionCompanyList;

    @ColumnInfo(name = "production_countries")
    @SerializedName("production_countries")
    @TypeConverters(Converters.class)
    @Nullable
    private List<ProductionCountry> productionCountryList;

    @ColumnInfo(name = "spoken_languages")
    @SerializedName("spoken_languages")
    @TypeConverters(Converters.class)
    @Nullable private List<SpokenLanguage> spokenLanguageList;

    @ColumnInfo(name = "created")
    private long created = System.currentTimeMillis();

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
    public String getOriginalTitle() {
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

    public void setTotalVote(int totalVote) {
        this.totalVote = totalVote;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAverageRatings(float averageRatings) {
        this.averageRatings = averageRatings;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setOriginalTitle(@Nullable String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverView(@Nullable String overView) {
        this.overView = overView;
    }

    public void setReleaseDate(@Nullable String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterUrl(@Nullable String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setProductionCompanyList(@Nullable List<ProductionCompany> productionCompanyList) {
        this.productionCompanyList = productionCompanyList;
    }

    public void setProductionCountryList(@Nullable List<ProductionCountry> productionCountryList) {
        this.productionCountryList = productionCountryList;
    }

    public void setSpokenLanguageList(@Nullable List<SpokenLanguage> spokenLanguageList) {
        this.spokenLanguageList = spokenLanguageList;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * Helper API which generate a static object for purpose of testing
     *
     * @return statically generated object
     */
    public static Movie buildForTest() {
        Random random = new Random();
        Movie movie = new Movie();
        movie.title = "title";
        movie.originalTitle = "title";
        movie.id = random.nextInt();
        movie.posterUrl = "url";
        movie.releaseDate = "213-123-232";
        movie.overView = "adkjgdjasbdsajdbsahjdksa";
        movie.averageRatings = random.nextFloat();
        movie.spokenLanguageList = new ArrayList<>();
        movie.productionCountryList = new ArrayList<>();
        movie.productionCompanyList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Movie movie = (Movie) o;

        if (totalVote != movie.totalVote) { return false; }
        if (id != movie.id) { return false; }
        if (Float.compare(movie.averageRatings, averageRatings) != 0) { return false; }
        if (title != null ? !title.equals(movie.title) : movie.title != null) { return false; }
        if (originalTitle != null ? !originalTitle.equals(movie.originalTitle) : movie.originalTitle != null) { return false; }
        if (overView != null ? !overView.equals(movie.overView) : movie.overView != null) { return false; }
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null) { return false; }
        if (posterUrl != null ? !posterUrl.equals(movie.posterUrl) : movie.posterUrl != null) { return false; }
        if (productionCompanyList != null ? !productionCompanyList.equals(movie.productionCompanyList) : movie.productionCompanyList !=
                null) {
            return false;
        }
        if (productionCountryList != null ? !productionCountryList.equals(movie.productionCountryList) : movie.productionCountryList !=
                null) {
            return false;
        }
        return spokenLanguageList != null ? spokenLanguageList.equals(movie.spokenLanguageList) : movie.spokenLanguageList == null;
    }

    @Override
    public int hashCode() {
        int result = totalVote;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (averageRatings != +0.0f ? Float.floatToIntBits(averageRatings) : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (overView != null ? overView.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (posterUrl != null ? posterUrl.hashCode() : 0);
        result = 31 * result + (productionCompanyList != null ? productionCompanyList.hashCode() : 0);
        result = 31 * result + (productionCountryList != null ? productionCountryList.hashCode() : 0);
        result = 31 * result + (spokenLanguageList != null ? spokenLanguageList.hashCode() : 0);
        return result;
    }
}
