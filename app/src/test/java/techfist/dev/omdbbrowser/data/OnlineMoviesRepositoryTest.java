package techfist.dev.omdbbrowser.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Single;
import techfist.dev.omdbbrowser.api.requests.DiscoverMoviesRequest;
import techfist.dev.omdbbrowser.api.requests.MovieDetailRequest;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;
import techfist.dev.omdbbrowser.api.service.MoviesService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class OnlineMoviesRepositoryTest {

    @Mock private MoviesService moviesService;
    private final String key = "12345";
    private OnlineMoviesRepository moviesRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        moviesRepository = new OnlineMoviesRepository(moviesService, key);
    }

    @Test
    public void test_loadMovies_failure() {
        Mockito.doReturn(Single.error(new RuntimeException()))
                .when(moviesService)
                .discoverPopularMovies(any(DiscoverMoviesRequest.class));

        moviesRepository.loadMovies()
                .test()
                .assertError(RuntimeException.class)
                .assertNoValues()
                .assertNotComplete()
                .dispose();
    }

    @Test
    public void test_loadMovies_success() {

        DiscoverResponse response = DiscoverResponse.buildForTest();
        DiscoverMoviesRequest request = new DiscoverMoviesRequest.Builder(key).setRequestedPage(1)
                .build();
        Mockito.doReturn(Single.just(response))
                .when(moviesService)
                .discoverPopularMovies(eq(request));

        moviesRepository.loadMovies()
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(response)
                .dispose();

        Assert.assertEquals(moviesRepository.maxPages, response.getTotalPages());
        Assert.assertEquals(moviesRepository.pageNumber, response.getPage());

    }

    @Test
    public void test_loadMoreMovies_reached_limit() {
        moviesRepository.maxPages = 1;
        moviesRepository.pageNumber = 2;

        moviesRepository.loadMoreMovies()
                .test()
                .assertError(RuntimeException.class)
                .assertNotComplete()
                .assertNoValues()
                .dispose();
    }

    @Test
    public void test_loadMoreMovies_network_error() {
        moviesRepository.maxPages = 10;
        moviesRepository.pageNumber = 1;

        Mockito.doReturn(Single.error(new RuntimeException()))
                .when(moviesService)
                .discoverPopularMovies(any(DiscoverMoviesRequest.class));

        moviesRepository.loadMoreMovies()
                .test()
                .assertError(RuntimeException.class)
                .assertNoValues()
                .assertNotComplete()
                .dispose();
        Assert.assertEquals(1, moviesRepository.pageNumber);
    }

    @Test
    public void test_loadMovieDetail_failure() {

        Mockito.doReturn(Single.error(new RuntimeException()))
                .when(moviesService)
                .getMovieDetail(any(MovieDetailRequest.class));

        moviesRepository.loadMovieDetail(1)
                .test()
                .assertError(RuntimeException.class)
                .assertNoValues()
                .assertNotComplete()
                .dispose();

    }

    @Test
    public void test_loadMovieDetail_invalid_id() {
        moviesRepository.loadMovieDetail(-1)
                .test()
                .assertError(IllegalArgumentException.class)
                .assertNoValues()
                .assertNotComplete()
                .dispose();
    }

    @Test
    public void load_loadMovieDetail_success() {
        Movie movie = Movie.buildForTest();
        MovieDetailRequest request = new MovieDetailRequest.Builder(key).setMovieId(1)
                .build();

        Mockito.doReturn(Single.just(movie))
                .when(moviesService)
                .getMovieDetail(request);

        moviesRepository.loadMovieDetail(1)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(movie)
                .assertComplete()
                .dispose();
    }
}