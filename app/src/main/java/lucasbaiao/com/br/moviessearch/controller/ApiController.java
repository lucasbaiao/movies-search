package lucasbaiao.com.br.moviessearch.controller;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import lucasbaiao.com.br.moviessearch.api.ApiMovieRouter;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDetailDto;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDto;
import lucasbaiao.com.br.moviessearch.api.dto.PagerDto;
import lucasbaiao.com.br.moviessearch.database.entity.Films;
import lucasbaiao.com.br.moviessearch.exception.NoNetworkConnectionException;
import io.reactivex.Observable;

public class ApiController {

    private static final String TAG = "ApiController";

    private final ApiMovieRouter restRouter;
    private final Context context;

    public ApiController(ApiMovieRouter restRouter, Context context) {
        this.restRouter = restRouter;
        this.context = context;
    }

    public Observable<Films> getFilmDetail(final long filmId) {
        return MultipleSourceService.buildExecutor(context, new MultipleSourceService.InnerService<Films>() {
            @Override
            public boolean localService(MultipleSourceService.InnerServiceContext<Films> context) {
                return true;
            }

            @Override
            public boolean remoteService(MultipleSourceService.InnerServiceContext<Films> context) {
                try {
                    FilmDetailDto result = restRouter.movie((int) filmId).execute().body();
                    Films cacheFilmDetail = cacheFilmDetail(result);
                    context.putRemoteData(cacheFilmDetail);
                } catch (IOException ex) {
                    Log.e(TAG, "Error on get film detail from server", ex);
                }
                return true;
            }

            @Override
            public void onNoRemoteCommunication(MultipleSourceService.InnerServiceContext<Films> context) {
                // se não tem dado local e não possui rede
                // lançar exceção na tela
                if (context.getLocalServiceData() == null) {
                    throw new NoNetworkConnectionException();
                }
            }
        });
    }


    public Observable<PagerDto<FilmDto>> getPopularFilms(final int page) {
        return MultipleSourceService.buildExecutor(context, new MultipleSourceService.InnerService<PagerDto<FilmDto>>() {
            @Override
            public boolean localService(MultipleSourceService.InnerServiceContext<PagerDto<FilmDto>> context) {
                return true; // pega dados remotos
            }

            @Override
            public boolean remoteService(MultipleSourceService.InnerServiceContext<PagerDto<FilmDto>> context) {
                try {
                    PagerDto<FilmDto> results = restRouter.populars(page).execute().body();
                    context.putRemoteData(results);
                    cacheFilms(results);
                } catch (IOException ex) {
                    Log.e(TAG, "Error on get popular films from server", ex);
                }
                return false;
            }

            @Override
            public void onNoRemoteCommunication(MultipleSourceService.InnerServiceContext<PagerDto<FilmDto>> context) {
                if (context.getLocalServiceData() == null) {
                    throw new NoNetworkConnectionException();
                }
            }
        });
    }

    public Observable<PagerDto<FilmDto>> getTopRated(final int page) {
        return MultipleSourceService.buildExecutor(context, new MultipleSourceService.InnerService<PagerDto<FilmDto>>() {
            @Override
            public boolean localService(MultipleSourceService.InnerServiceContext<PagerDto<FilmDto>> context) {
                return true;
            }

            @Override
            public boolean remoteService(MultipleSourceService.InnerServiceContext<PagerDto<FilmDto>> context) {
                try {
                    PagerDto<FilmDto> result = restRouter.topRated(page).execute().body();
                    context.putRemoteData(result);
                    cacheFilms(result);
                } catch ( IOException ex) {
                    Log.e(TAG, "Error on get top rated films from server", ex);
                }
                return true;
            }

            @Override
            public void onNoRemoteCommunication(MultipleSourceService.InnerServiceContext<PagerDto<FilmDto>> context) {
                if (context.getLocalServiceData() == null) {
                    throw new NoNetworkConnectionException();
                }
            }
        });
    }

    private void cacheFilms(PagerDto<FilmDto> apiResults) {
        FilmDatabaseController controller = new FilmDatabaseController(context);
        controller.save(apiResults.results);
    }

    private Films cacheFilmDetail(FilmDetailDto result) {
        FilmDatabaseController controller = new FilmDatabaseController(context);
        return controller.saveDetail(result);
    }
}
