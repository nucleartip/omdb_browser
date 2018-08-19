package techfist.dev.omdbbrowser.ui.landing;

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
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.SingleSubject;
import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.api.response.DiscoverResponse;
import techfist.dev.omdbbrowser.api.response.Movie;
import techfist.dev.omdbbrowser.data.MoviesRepository;
import techfist.dev.omdbbrowser.data.OnlineMoviesRepository;
import techfist.dev.omdbbrowser.data.ResourceProvider;

import static techfist.dev.omdbbrowser.ApplicationConstant.EMPTY;
import static techfist.dev.omdbbrowser.ui.landing.MovieModel.PROFILE_URL_FORMAT;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MoviesActivityViewModelTest {

    @Mock private ResourceProvider resourceProvider;
    @Mock private MoviesRepository moviesRepository;
    private MoviesAdapter moviesAdapter = new MoviesAdapter();
    private MoviesActivityViewModel moviesActivityViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.doReturn("error")
                .when(resourceProvider)
                .getString(R.string.error);

        Mockito.doReturn("loading")
                .when(resourceProvider)
                .getString(R.string.loading_looking_up_result);

        moviesActivityViewModel = Mockito.spy(new MoviesActivityViewModel(moviesRepository, resourceProvider, moviesAdapter));

        Mockito.doReturn(Schedulers.trampoline())
                .when(moviesActivityViewModel)
                .getIoScheduler();
        Mockito.doReturn(Schedulers.trampoline())
                .when(moviesActivityViewModel)
                .getMainThreadScheduler();

        Assert.assertEquals(EMPTY, moviesActivityViewModel.errorText.get());
        Assert.assertEquals(EMPTY, moviesActivityViewModel.loadingText.get());
        Assert.assertFalse(moviesActivityViewModel.showLoadingMore.get());

    }

    @Test
    public void test_onScreenInit_error(){
        Mockito.doReturn(Single.error(new RuntimeException()))
                .when(moviesRepository)
                .loadMovies();

        moviesActivityViewModel.onScreenInit();
        Assert.assertEquals("error", moviesActivityViewModel.errorText.get());
        Assert.assertEquals(EMPTY, moviesActivityViewModel.loadingText.get());
    }

    @Test
    public void test_onScreenInit_success(){
        SingleSubject<DiscoverResponse> subject = SingleSubject.create();
        Mockito.doReturn(subject)
                .when(moviesRepository)
                .loadMovies();

        moviesActivityViewModel.onScreenInit();
        Assert.assertEquals("", moviesActivityViewModel.errorText.get());
        Assert.assertEquals("loading", moviesActivityViewModel.loadingText.get());
        Assert.assertNull(moviesAdapter.items);
        subject.onSuccess(getSampleResponse());
        Assert.assertNotNull(moviesAdapter.items);
        Assert.assertNotNull(moviesActivityViewModel.loader);
        moviesActivityViewModel.loader.dispose();
    }


    @Test
    public void test_loadMovies_success() {
        DiscoverResponse response = getSampleResponse();
        Mockito.doReturn(Single.just(response))
                .when(moviesRepository)
                .loadMovies();

        TestObserver<List<MovieModel>> disposable = moviesActivityViewModel.loadMovies()
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete();
        List<MovieModel> models = disposable.values()
                .get(0);
        assert response.getMoviesList() != null;
        Assert.assertEquals(models.size(), response.getMoviesList()
                .size());

        List<Movie> original = response.getMoviesList();
        for (int i = 0; i < models.size(); i++) {
            MovieModel model = models.get(i);
            Movie movie = original.get(i);
            Assert.assertEquals(model.getId(), movie.getId());
            Assert.assertEquals(model.getImageUrl(), String.format(PROFILE_URL_FORMAT, movie.getPosterUrl()));
            Assert.assertEquals(model.getTitle(), movie.getTitle());
        }
        disposable.dispose();
    }

    @Test
    public void test_loadMovies_empty_list_or_null() {
        Mockito.doReturn(Single.just(new DiscoverResponse()))
                .when(moviesRepository)
                .loadMovies();

        TestObserver<List<MovieModel>> disposable = moviesActivityViewModel.loadMovies()
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete();

        Assert.assertEquals(0, disposable.values()
                .get(0)
                .size());
        disposable.dispose();
    }

    @Test
    public void test_loadMovies_error() {
        Mockito.doReturn(Single.error(new RuntimeException()))
                .when(moviesRepository)
                .loadMovies();

        moviesActivityViewModel.loadMovies()
                .test()
                .assertNoValues()
                .assertError(RuntimeException.class)
                .assertNotComplete()
                .dispose();
    }

    private DiscoverResponse getSampleResponse() {
        return DiscoverResponse.buildForTest();
    }

}
