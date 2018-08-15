package techfist.dev.omdbbrowser.ui;

import techfist.dev.omdbbrowser.data.ResourceProvider;

public class MovieModel {

    private final String title;
    private final String year;
    private final String imageUrl;
    private final ResourceProvider resourceProvider;

    public MovieModel(ResourceProvider resourceProvider, String title, String year, String imageUrl) {
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
        this.resourceProvider = resourceProvider;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
