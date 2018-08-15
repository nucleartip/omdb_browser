package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("overview")
    @Nullable private
    String overView;

    @SerializedName("release_date")
    @Nullable
    private String releaseDate;

    @SerializedName("backdrop_path")
    @Nullable
    private String posterUrl;


    public void setTotalVote(int totalVote) {
        this.totalVote = totalVote;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAverageRatings(float averageRatings) {
        this.averageRatings = averageRatings;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getTotalVote() {
        return totalVote;
    }

    public long getId() {
        return id;
    }

    public float getAverageRatings() {
        return averageRatings;
    }

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" + "totalVote=" + totalVote + ", id=" + id + ", averageRatings=" + averageRatings + ", title='" + title + '\'' +
                ", overView='" + overView + '\'' + ", releaseDate='" + releaseDate + '\'' + ", posterUrl='" + posterUrl + '\'' + '}';
    }
}
