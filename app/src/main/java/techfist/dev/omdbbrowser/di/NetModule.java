package techfist.dev.omdbbrowser.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import techfist.dev.omdbbrowser.AppComponent;
import techfist.dev.omdbbrowser.api.service.MoviesApi;
import techfist.dev.omdbbrowser.api.service.MoviesService;
import techfist.dev.omdbbrowser.api.service.MoviesServiceImpl;

/**
 * you can read more about dependency injection here, for this app am using
 * one of most popular solution Dagger https://google.github.io/dagger/users-guide
 *
 * provides app-wide dependencies, hosts mostly network related dependencies
 */
@Module
public class NetModule {

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(HTTP_LOGGING_INTERCEPTOR);
    }

    private static <T> T getRetrofit(OkHttpClient.Builder okHttpClientBuilder, String hostName, Class<T> clazz) {

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setLenient()
                .setPrettyPrinting()
                .create();

        return new Retrofit.Builder().client(okHttpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(hostName)
                .build()
                .create(clazz);
    }

    @Provides
    @AppComponent.AppScope
    MoviesApi provideMoviesApi() {
        return getRetrofit(getOkHttpClientBuilder(), MoviesApi.ENDPOINT, MoviesApi.class);
    }


    @Provides
    @AppComponent.AppScope
    MoviesService providesMoviesService(MoviesApi moviesApi) {
        return new MoviesServiceImpl(moviesApi);
    }

}
