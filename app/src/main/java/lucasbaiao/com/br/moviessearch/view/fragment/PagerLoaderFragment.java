package lucasbaiao.com.br.moviessearch.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.view.VerticalLineDecorator;
import lucasbaiao.com.br.moviessearch.view.adapter.PagerAdapter;

public abstract class PagerLoaderFragment<Adapter extends RecyclerView.Adapter, Results, T>
        extends BaseLoaderFragment<T> implements PagerAdapter.OnLoadMoreListener {

    private static final int COLUMNS = 2;
    @Bind(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @Bind(R.id.progressLayout)
    protected RelativeLayout progressLayout;
    @Bind(R.id.errorLayout)
    protected RelativeLayout errorLayout;

    private int pageIndex = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Results> dataList = new ArrayList<>();
    private boolean isLoading;

    protected int getPageIndex() {
        return this.pageIndex;
    }

    List<Results> getData() {
        return this.dataList;
    }

    PagerAdapter.OnLoadMoreListener getMoreDataListener() {
        return this;
    }

    public OnListFragmentInteractionListener getFragmentListener() {
        return mListener;
    }

    protected abstract void onDataLoaded(T value);

    protected abstract RecyclerView.Adapter getAdapter();

    @OnClick(R.id.try_again)
    void onTryAgainClick(View view) {
        this.resetValues();
        this.didLoadData();
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_movies_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setupRecyclerView(view);
        if (this.dataList == null || this.dataList.isEmpty()) {
            this.didLoadData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadMore() {
        this.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                pageIndex += 1;
                didLoadData();
            }
        });
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void showLoading(boolean isVisible) {
        this.progressLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        this.recyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);

        this.isLoading = isVisible;
    }

    private void showError(Throwable error) {
        this.showError(error.getMessage());
    }

    private void showError(String error) {
        this.showLoading(false);
        if (this.dataList.isEmpty()) {
            this.recyclerView.setVisibility(View.GONE);
            this.errorLayout.setVisibility(View.VISIBLE);
            ((TextView) this.errorLayout.findViewById(R.id.errorMessage)).setText(error);
        } else {
            this.mListener.makeSnackBar(error);
        }
    }

    @Override
    public void refreshData() {
        this.resetValues();
        this.didLoadData();
    }

    @Override
    public void didLoadData() {
        if (!this.isLoading) {
            if (pageIndex > 1) {
                this.dataList.add(null);
                this.recyclerView.getAdapter().notifyItemInserted(this.dataList.size() - 1);
            } else {
                this.showLoading(true);
            }

            this.getLoaderData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DataLoaderObserver());
        }
    }

    private void setupRecyclerView(View view) {
        this.resetValues();
        final GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), COLUMNS);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(this.getAdapter());
        this.recyclerView.addItemDecoration(new VerticalLineDecorator(2));
    }

    private void resetValues() {
        this.dataList.clear();
        this.pageIndex = 1;
    }

    public interface OnListFragmentInteractionListener {

        void onFavoriteSelected(int filmId, boolean isChecked);
        void onItemSelected(int filmId, ImageView imageView);
        void makeSnackBar(String message);

    }

    private class DataLoaderObserver implements Observer<T> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T value) {
            if (value instanceof List) {
                if (((List) value).isEmpty()) {
                    ((PagerAdapter) recyclerView.getAdapter()).setMoreDataAvailable(false);
                    mListener.makeSnackBar(getString(R.string.no_more_data_available));
                }
            }
            PagerLoaderFragment.this.onDataLoaded(value);
        }

        @Override
        public void onError(Throwable e) {
            PagerLoaderFragment.this.showError(e);
        }

        @Override
        public void onComplete() {
            PagerLoaderFragment.this.showLoading(false);
        }

    }
}
