package techfist.dev.omdbbrowser.ui.landing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import techfist.dev.omdbbrowser.R;
import techfist.dev.omdbbrowser.data.ResourceProvider;

import static techfist.dev.omdbbrowser.ui.landing.MovieModel.PROFILE_URL_FORMAT;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MovieModelTest {

    @Mock
    private ResourceProvider resourceProvider;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test_initialization(){

        Mockito.doReturn("rating")
                .when(resourceProvider)
                .getString(R.string.rating, String.format("%.2f", 4.467f));

        Mockito.doReturn("released")
                .when(resourceProvider)
                .getString(R.string.released, "2018");

        MovieModel movieModel = new MovieModel(resourceProvider,
                1, "title", "2018-08-22", "url", 4.467f);
        Assert.assertEquals(1, movieModel.getId());
        Assert.assertEquals("released", movieModel.getYear());
        Assert.assertEquals(movieModel.getImageUrl(), String.format(PROFILE_URL_FORMAT, "url"));
        Assert.assertEquals("title", movieModel.getTitle());
        Assert.assertEquals("rating", movieModel.getRating());
    }
}
