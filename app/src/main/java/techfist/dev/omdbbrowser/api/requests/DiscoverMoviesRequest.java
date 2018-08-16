package techfist.dev.omdbbrowser.api.requests;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import techfist.dev.omdbbrowser.api.SortType;

/**
 * Builder class for building valid movie discover request
 */
public class DiscoverMoviesRequest {
    private String apikey;
    private @SortType String sortingType;
    private int requestedPage;


    private DiscoverMoviesRequest() {

    }

    @NonNull
    public String getApikey() {
        return apikey;
    }

    @SortType
    @NonNull
    public String getSortingType() {
        return sortingType;
    }

    public int getRequestedPage() {
        return requestedPage;
    }

    public static class Builder {

        private String apikey;
        private String sortingType = SortType.POPULARITY_DESC;
        private int requestedPage = 1;


        public Builder(@NonNull String apiKey) {
            this.apikey = apiKey;
        }

        public Builder setSortingType(@NonNull @SortType String sortingType) {


            if (TextUtils.isEmpty(sortingType)) {
                return this;
            }

            this.sortingType = sortingType;
            return this;
        }


        public Builder setRequestedPage(int page) {
            if (page <= 0) {
                page = 1;
            }
            this.requestedPage = page;
            return this;
        }


        public DiscoverMoviesRequest build() {

            DiscoverMoviesRequest request = new DiscoverMoviesRequest();

            // defensive check second in line, though annotated filed will already warn user if
            // invalid data is being passed as first line of defense.

            if (TextUtils.isEmpty(apikey)) {
                throw new IllegalArgumentException("api key is empty of null");
            }

            request.apikey = this.apikey;
            request.sortingType = this.sortingType;
            request.requestedPage = this.requestedPage;
            return request;
        }

    }
}
