package techfist.dev.omdbbrowser.api.response;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * A response structure of discover request, am assuming few fields might be null or empty
 * as server might not provide them in response.
 * <p>
 * only those which might be required for this task are being parsed here.
 * <p>
 */

public class DiscoverResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    @Nullable
    private List<Movie> moviesList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Nullable
    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(@Nullable List<Movie> moviesList) {
        this.moviesList = moviesList;
    }
}
