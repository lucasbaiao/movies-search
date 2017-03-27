package lucasbaiao.com.br.moviessearch.database;

import android.util.SparseArray;

import com.android.internal.util.Predicate;

import java.util.List;

import lucasbaiao.com.br.moviessearch.database.entity.BaseEntity;
import lucasbaiao.com.br.moviessearch.database.entity.Films;

public interface IFilmHelper<T extends BaseEntity> extends IHelper<T> {

    SparseArray<T> findFavorites(Predicate<T> predicate);
}
