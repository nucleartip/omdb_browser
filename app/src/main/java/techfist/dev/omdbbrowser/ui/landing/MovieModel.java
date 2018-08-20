package techfist.dev.omdbbrowser.ui.landing;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.data.ResourceProvider;
import techfist.dev.omdbbrowser.ui.detail.MovieDetailActivity;

/**
 * View model representing a displayable Movie tile
 *
 * works by using android data binding library, this model is binded with UI
 * from {@link MoviesAdapter}
 *
 * this class has been unit tested
 */
public class MovieModel {

    @VisibleForTesting final static String PROFILE_URL_FORMAT = "https://image.tmdb.org/t/p/w300/%1$s";
    private final long id;
    private final String title;
    private final String year;
    private final String imageUrl;
    private final float rating;
    private final ResourceProvider resourceProvider;

    public MovieModel(@NonNull ResourceProvider resourceProvider,
                      long id,
                      @NonNull String title,
                      @NonNull String year,
                      @NonNull String imageUrl,
                      float rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.resourceProvider = resourceProvider;
    }

    // Below API's, are accessed via data binding


    public String getTitle() {
        return title;
    }

    public String getYear() {
        return resourceProvider.getString(R.string.released, year.split("-")[0]);
    }

    public String getImageUrl() {
        return String.format(PROFILE_URL_FORMAT, imageUrl);
    }

    @SuppressLint("DefaultLocale")
    public String getRating() {
        return resourceProvider.getString(R.string.rating, String.format("%.2f", rating));
    }

    public long getId() {
        return id;
    }

    /**
     * opens up detail activity for clicked movie
     * @param view which was clicked
     *
     */
    public void onClick(@Nullable View view) {
        if (view == null || view.getContext() == null) {
            return;
        }

        view.getContext()
                .startActivity(MovieDetailActivity.newIntent(view.getContext(), id));
    }
}