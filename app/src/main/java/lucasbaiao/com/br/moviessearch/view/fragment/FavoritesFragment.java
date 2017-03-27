package lucasbaiao.com.br.moviessearch.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.controller.FilmDatabaseController;
import lucasbaiao.com.br.moviessearch.database.entity.Films;
import lucasbaiao.com.br.moviessearch.view.adapter.FavoritesAdapter;
import lucasbaiao.com.br.moviessearch.view.adapter.PagerAdapter;

public class FavoritesFragment extends PagerLoaderFragment<FavoritesAdapter, Films, List<Films>> {

    @Inject
    FilmDatabaseController controller;

    public FavoritesFragment() {
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onDataLoaded(List<Films> value) {
        int dataSize = this.getData().size();
        if (dataSize > 0 && this.getData().get(dataSize - 1) == null) {
            //remove loading view
            this.getData().remove(dataSize - 1);
        }

        this.getData().clear();
        this.getData().addAll(value);
        ((PagerAdapter) this.recyclerView.getAdapter()).notifyDataChanged();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new FavoritesAdapter(getData(), null, getFragmentListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        super.getApplicationComponent().inject(this);
        super.setTitle(R.string.title_favorite_fragment);
        return view;
    }

    @Override
    public Observable<List<Films>> getLoaderData() {
        return this.controller.getAllFavorites();
    }
}
