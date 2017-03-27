package lucasbaiao.com.br.moviessearch.database;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import lucasbaiao.com.br.moviessearch.database.entity.BaseEntity;

abstract class BaseHelper<T extends BaseEntity> implements IHelper<T> {

    abstract String getBookName();

    @Override
    public boolean delete(Predicate<T> predicate) {
        List<String> keys = Paper.book(this.getBookName()).getAllKeys();
        for(String key: keys) {
            T entity = Paper.book(this.getBookName()).read(key);
            if (predicate.apply(entity)) {
                Paper.book(this.getBookName()).delete(key);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<T> find(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        List<String> keys = Paper.book(this.getBookName()).getAllKeys();
        for(String key: keys) {
            T entity = Paper.book(this.getBookName()).read(key);
            if (predicate.apply(entity)) {
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public T find(String id) {
        return Paper.book(getBookName()).read(id);
    }

    @Override
    public void insert(T record) {
        String uuid = record.getUUID();
        Paper.book(this.getBookName()).write(uuid, record);
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        List<String> keys = Paper.book(this.getBookName()).getAllKeys();
        for(String key: keys) {
            T entity = Paper.book(this.getBookName()).read(key);
            result.add(entity);
        }
        return result;
    }
}
