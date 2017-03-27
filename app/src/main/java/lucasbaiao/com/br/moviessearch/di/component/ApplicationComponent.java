package lucasbaiao.com.br.moviessearch.di.component;

import javax.inject.Singleton;

import dagger.Component;
import lucasbaiao.com.br.moviessearch.controller.ApiController;
import lucasbaiao.com.br.moviessearch.controller.FilmDatabaseController;
import lucasbaiao.com.br.moviessearch.di.module.ApplicationModule;
import lucasbaiao.com.br.moviessearch.view.fragment.FavoritesFragment;
import lucasbaiao.com.br.moviessearch.view.FilmDetailsActivity;
import lucasbaiao.com.br.moviessearch.view.MoviesActivity;
import lucasbaiao.com.br.moviessearch.view.fragment.PopularFragment;
import lucasbaiao.com.br.moviessearch.view.fragment.TopRatedFragment;

@Component(modules = ApplicationModule.class, dependencies = ApiComponent.class)
@Singleton
public interface ApplicationComponent {

    ApiController apiController();

    FilmDatabaseController filmDatabaseController();

    void inject(PopularFragment moviesListFragment);

    void inject(TopRatedFragment topRatedFragment);

    void inject(FilmDetailsActivity filmDetailsActivity);

    void inject(MoviesActivity moviesActivity);

    void inject(FavoritesFragment favoritesFragment);
}
