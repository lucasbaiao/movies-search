package lucasbaiao.com.br.moviessearch.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDto;
import lucasbaiao.com.br.moviessearch.api.dto.PagerDto;
import lucasbaiao.com.br.moviessearch.controller.ApiController;
import lucasbaiao.com.br.moviessearch.controller.FilmDatabaseController;
import lucasbaiao.com.br.moviessearch.database.entity.Films;
import lucasbaiao.com.br.moviessearch.view.adapter.PagerAdapter;
import lucasbaiao.com.br.moviessearch.view.adapter.TopRatedAdapter;

public class TopRatedFragment extends PagerLoaderFragment<TopRatedAdapter, Films, PagerDto<Films>> {

    @Inject
    ApiController apiController;
    @Inject
    FilmDatabaseController databaseController;

    public TopRatedFragment() {
    }

    public static TopRatedFragment newInstance() {
        TopRatedFragment fragment = new TopRatedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onDataLoaded(PagerDto<Films> value) {
        int dataSize = this.getData().size();
        if (dataSize > 0 && this.getData().get(dataSize - 1) == null) {
            //remove loading view
            this.getData().remove(dataSize - 1);
        }

        this.getData().addAll(value.results);
        ((PagerAdapter) this.recyclerView.getAdapter()).notifyDataChanged();

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new TopRatedAdapter(getData(), getMoreDataListener(), getFragmentListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        super.getApplicationComponent().inject(this);
        super.setTitle(R.string.title_top_rated_fragment);
        return view;
    }

    @Override
    public Observable<PagerDto<Films>> getLoaderData() {
        return Observable.zip(this.apiController.getTopRated(this.getPageIndex()), databaseController.getFavorites(), new BiFunction<PagerDto<FilmDto>, SparseArray<Films>, PagerDto<Films>>() {
            @Override
            public PagerDto<Films> apply(PagerDto<FilmDto> dto, SparseArray<Films> favorites) throws Exception {
                List<Films> list = new ArrayList<>();
                for (FilmDto filmDto: dto.results) {
                    boolean isFavorite = favorites.get((int) filmDto.id) != null;
                    list.add(new Films.Builder().fromDto(filmDto).favorite(isFavorite).build());
                }
                return new PagerDto<>(dto.totalResults, dto.totalPages, dto.page, list);
            }
        });
    }
}
