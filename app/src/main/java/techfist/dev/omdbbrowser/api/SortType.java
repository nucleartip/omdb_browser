package techfist.dev.omdbbrowser.api;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static techfist.dev.omdbbrowser.api.SortType.POPULARITY_ASC;
import static techfist.dev.omdbbrowser.api.SortType.POPULARITY_DESC;

/**
 * Predefined sorting formats acceptable by TMDB API's
 * More info can be found here https://www.themoviedb.org/documentation/api
 */
@StringDef({POPULARITY_DESC, POPULARITY_ASC})
@Retention(RetentionPolicy.SOURCE)
public @interface SortType {

    String POPULARITY_DESC = "popularity.desc";
    String POPULARITY_ASC = "popularity.asc";
}
