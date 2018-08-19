package techfist.dev.omdbbrowser.api.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A response structure of discover request, am assuming few fields might be null or empty
 * as server might not provide them in response.
 * <p>
 * only those which might be required for this task are being parsed here.
 * <p>
 */

public class DiscoverResponse {

    /**
     * represents current page being viewed
     */
    @SerializedName("page")
    private int page;

    /**
     * represents total number of pages in directory which can be viewed
     */
    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    @Nullable
    private List<Movie> moviesList;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Nullable
    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setMoviesList(@Nullable List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    /**
     * Helper API to generate a sample discover response for purpose of test
     * @return a static generated object
     */
    public static DiscoverResponse buildForTest() {
        Random random = new Random();
        DiscoverResponse response = new DiscoverResponse();
        response.page = random.nextInt(10);
        response.totalPages = 10 + random.nextInt();
        response.moviesList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            response.moviesList.add(Movie.buildForTest());
        }
        return response;
    }
}
