package lucasbaiao.com.br.moviessearch.database;

import android.util.SparseArray;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import lucasbaiao.com.br.moviessearch.database.entity.Films;

public class FilmDatabaseHelper extends BaseHelper<Films> implements IFilmHelper<Films> {

    private static final String BOOK_NAME = "collection_films";

    @Override
    String getBookName() {
        return BOOK_NAME;
    }

    @Override
    public SparseArray<Films> findFavorites(Predicate<Films> predicate) {
        SparseArray<Films> result = new SparseArray<>();
        List<String> keys = Paper.book(this.getBookName()).getAllKeys();
        for(String key: keys) {
            Films entity = Paper.book(this.getBookName()).read(key);
            if (predicate.apply(entity)) {
                result.put((int) entity.getId(), entity);
            }
        }
        return result;
    }
}
