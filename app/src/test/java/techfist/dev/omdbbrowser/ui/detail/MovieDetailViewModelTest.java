package techfist.dev.omdbbrowser.ui.detail;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.api.response.Movie;
import techfist.dev.omdbbrowser.api.response.ProductionCompany;
import techfist.dev.omdbbrowser.api.response.ProductionCountry;
import techfist.dev.omdbbrowser.api.response.SpokenLanguage;
import techfist.dev.omdbbrowser.data.MoviesRepository;
import techfist.dev.omdbbrowser.data.OnlineMoviesRepository;
import techfist.dev.omdbbrowser.data.ResourceProvider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static techfist.dev.omdbbrowser.ApplicationConstant.EMPTY;
import static techfist.dev.omdbbrowser.ui.detail.MovieDetailViewModel.PROFILE_URL_FORMAT;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MovieDetailViewModelTest {

    @Mock private MoviesRepository moviesRepository;
    @Mock private ResourceProvider resourceProvider;
    private MovieDetailViewModel movieDetailViewModel;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn("error")
                .when(resourceProvider)
                .getString(R.string.error);

        Mockito.doReturn("loading")
                .when(resourceProvider)
                .getString(R.string.loading_details);
        movieDetailViewModel = Mockito.spy(new MovieDetailViewModel(moviesRepository, resourceProvider));

        Mockito.doReturn(Schedulers.trampoline())
                .when(movieDetailViewModel)
                .getIoScheduler();
        Mockito.doReturn(Schedulers.trampoline())
                .when(movieDetailViewModel)
                .getMainThreadScheduler();

        Assert.assertEquals(EMPTY, movieDetailViewModel.loadingText.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.errorText.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.releasedDate.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.ratings.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.overview.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.producedBy.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.spokenLanguages.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.countryOfOrigin.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.posterUrl.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.title.get());
    }

    @Test
    public void test_onScreenInit_failure() {
        Mockito.doReturn(Single.error(new RuntimeException()))
                .when(moviesRepository)
                .loadMovieDetail(1);

        movieDetailViewModel.onScreenInit();
        Assert.assertNotNull(movieDetailViewModel.detailLoader);
        Assert.assertEquals("loading", movieDetailViewModel.loadingText.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.errorText.get());
        movieDetailViewModel.movieId.set(1L);
        Assert.assertEquals(EMPTY, movieDetailViewModel.loadingText.get());
        Assert.assertEquals("error", movieDetailViewModel.errorText.get());
    }

    @Test
    public void test_onScreenInit_success() {
        Movie movie = Movie.buildForTest();

        Mockito.doReturn(Single.just(movie))
                .when(moviesRepository)
                .loadMovieDetail(1);

        Mockito.doReturn("overview")
                .when(resourceProvider)
                .getString(R.string.synopsis, movie.getOverView());
        Mockito.doReturn("released")
                .when(resourceProvider)
                .getString(R.string.released, movie.getReleaseDate());

        Mockito.doReturn("rating")
                .when(resourceProvider)
                .getString(eq(R.string.rating_detail), anyString());

        String countries = countryList(movie.getProductionCountryList());
        String languages = languageList(movie.getSpokenLanguageList());
        String companies = companiesList(movie.getProductionCompanyList());

        Mockito.doReturn("country")
                .when(resourceProvider)
                .getString(R.string.origin_country, countries);

        Mockito.doReturn("companies")
                .when(resourceProvider)
                .getString(R.string.produce_by, companies);

        Mockito.doReturn("language")
                .when(resourceProvider)
                .getString(R.string.spoken_languages, languages);
        movieDetailViewModel.onScreenInit();
        Assert.assertNotNull(movieDetailViewModel.detailLoader);
        Assert.assertEquals("loading", movieDetailViewModel.loadingText.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.errorText.get());
        movieDetailViewModel.movieId.set(1L);
        Assert.assertEquals(EMPTY, movieDetailViewModel.loadingText.get());
        Assert.assertEquals(EMPTY, movieDetailViewModel.errorText.get());

        Assert.assertEquals(movie.getOriginalTitle(), movieDetailViewModel.title.get());
        Assert.assertEquals("released", movieDetailViewModel.releasedDate.get());
        Assert.assertEquals("overview", movieDetailViewModel.overview.get());
        Assert.assertEquals(String.format(PROFILE_URL_FORMAT, movie.getPosterUrl()), movieDetailViewModel.posterUrl.get());
        Assert.assertEquals("country", movieDetailViewModel.countryOfOrigin.get());
        Assert.assertEquals("companies", movieDetailViewModel.producedBy.get());
        Assert.assertEquals("language", movieDetailViewModel.spokenLanguages.get());
    }

    private String companiesList(List<ProductionCompany> companyList) {
        if (companyList != null) {
            StringBuilder builder = new StringBuilder();
            int size = companyList.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(companyList.get(i)
                        .getName());
            }
            return builder.toString();
        }

        return "";
    }

    private String countryList(List<ProductionCountry> companyList) {
        if (companyList != null) {
            StringBuilder builder = new StringBuilder();
            int size = companyList.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(companyList.get(i)
                        .getName());
            }
            return builder.toString();
        }

        return "";
    }

    private String languageList(List<SpokenLanguage> companyList) {
        if (companyList != null) {
            StringBuilder builder = new StringBuilder();
            int size = companyList.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(companyList.get(i)
                        .getName());
            }
            return builder.toString();
        }

        return "";
    }

}