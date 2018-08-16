package techfist.dev.omdbbrowser.api.requests;


import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Builder for building valid movie detail request
 */
public class MovieDetailRequest {
    @NonNull private String apikey = "";
    private long movieId;


    private MovieDetailRequest() {

    }

    @NonNull
    public String getApikey() {
        return apikey;
    }

    public long getMovieId() {
        return movieId;
    }

    public static class Builder {

        private String apikey;
        private long movieId = 0;


        public Builder(@NonNull String apiKey) {
            this.apikey = apiKey;
        }


        public MovieDetailRequest.Builder setMovieId(long id) {
            this.movieId = id;
            return this;
        }


        public MovieDetailRequest build() {

            MovieDetailRequest request = new MovieDetailRequest();

            // defensive check second in line, though annotated filed will already warn user if
            // invalid data is being passed as first line of defense.

            if (TextUtils.isEmpty(apikey)) {
                throw new IllegalArgumentException("api key is empty of null");
            }

            if (movieId < 0) {
                throw new IllegalArgumentException("movie id is illegal");
            }

            request.apikey = this.apikey;
            request.movieId = this.movieId;
            return request;
        }

    }
}
