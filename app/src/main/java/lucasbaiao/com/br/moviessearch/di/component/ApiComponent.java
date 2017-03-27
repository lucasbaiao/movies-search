package lucasbaiao.com.br.moviessearch.di.component;

import dagger.Component;
import lucasbaiao.com.br.moviessearch.api.ApiMovieRouter;
import lucasbaiao.com.br.moviessearch.di.module.ApiModule;

@Component(modules = ApiModule.class)
public interface ApiComponent {

    ApiMovieRouter apiMovieRouter();
}
