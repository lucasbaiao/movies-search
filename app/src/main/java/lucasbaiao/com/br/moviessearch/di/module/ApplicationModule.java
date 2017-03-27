package lucasbaiao.com.br.moviessearch.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lucasbaiao.com.br.moviessearch.AppApplication;
import lucasbaiao.com.br.moviessearch.api.ApiMovieRouter;
import lucasbaiao.com.br.moviessearch.controller.ApiController;
import lucasbaiao.com.br.moviessearch.controller.FilmDatabaseController;

@Module
public class ApplicationModule {
    private final AppApplication application;

    public ApplicationModule(AppApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context context() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    ApiController apiController(Context context, ApiMovieRouter router) {
        return new ApiController(router, context);
    }

    @Provides
    @Singleton
    FilmDatabaseController filmDatabaseController(Context context) {
        return new FilmDatabaseController(context);
    }
}
