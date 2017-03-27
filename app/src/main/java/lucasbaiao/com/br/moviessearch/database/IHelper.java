package lucasbaiao.com.br.moviessearch.database;

import com.android.internal.util.Predicate;

import lucasbaiao.com.br.moviessearch.database.entity.BaseEntity;

interface IHelper<T extends BaseEntity> {

    boolean delete(Predicate<T> predicate);

    java.util.List<T> find(Predicate<T> predicate);

    T find(String id);

    java.util.List<T> findAll();

    void insert(T record);

}
