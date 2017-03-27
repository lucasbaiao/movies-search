package lucasbaiao.com.br.moviessearch.controller;

import android.content.Context;
import android.util.SparseArray;

import com.android.internal.util.Predicate;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDetailDto;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDto;
import lucasbaiao.com.br.moviessearch.database.FilmDatabaseHelper;
import lucasbaiao.com.br.moviessearch.database.entity.FilmDetail;
import lucasbaiao.com.br.moviessearch.database.entity.Films;

public class FilmDatabaseController {

    private Context context;

    public FilmDatabaseController(Context context) {
        this.context = context;
    }

    public Observable<SparseArray<Films>> getFavorites() {
        return Observable.create(new ObservableOnSubscribe<SparseArray<Films>>() {
            @Override
            public void subscribe(ObservableEmitter<SparseArray<Films>> e) throws Exception {
                try {
                    FilmDatabaseHelper helper = new FilmDatabaseHelper();
                    final SparseArray<Films> filmsList = helper.findFavorites(new Predicate<Films>() {
                        @Override
                        public boolean apply(Films films) {
                            return films.isFavorite();
                        }
                    });
                    e.onNext(filmsList);
                } finally {
                    e.onComplete();
                }
            }
        });
    }

    public Observable<List<Films>> getAllFavorites() {
        return Observable.create(new ObservableOnSubscribe<List<Films>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Films>> e) throws Exception {
                try {
                    FilmDatabaseHelper helper = new FilmDatabaseHelper();
                    final List<Films> filmsList = helper.find(new Predicate<Films>() {
                        @Override
                        public boolean apply(Films films) {
                            return films != null && films.isFavorite();
                        }
                    });
                    e.onNext(filmsList);
                } finally {
                    e.onComplete();
                }
            }
        });
    }

    public void save(List<FilmDto> list) {
        FilmDatabaseHelper helper = new FilmDatabaseHelper();
        for(FilmDto dto : list) {
            Films temp = helper.find(String.valueOf(dto.id));
            Films film = new Films.Builder().fromDto(dto).favorite(temp!= null && temp.isFavorite()).build();
            helper.insert(film);
        }
    }

    public Films saveDetail(FilmDetailDto detail) {
        FilmDatabaseHelper helper = new FilmDatabaseHelper();

        Films film = helper.find(String.valueOf(detail.id));
        FilmDetail filmDetail = new FilmDetail.Builder().fromDto(detail).build();
        film.setFilmDetail(filmDetail);

        helper.insert(film);

        return film;
    }

    public void saveFavorite(int filmID, boolean isFavorite) {
        FilmDatabaseHelper helper = new FilmDatabaseHelper();

        Films film = helper.find(String.valueOf(filmID));
        film.setFavorite(isFavorite);

        helper.insert(film);
    }
}
