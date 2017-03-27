package lucasbaiao.com.br.moviessearch.view.fragment;

import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;
import lucasbaiao.com.br.moviessearch.di.component.ApplicationComponent;
import lucasbaiao.com.br.moviessearch.view.BaseActivity;

public abstract class BaseLoaderFragment<T> extends BaseFragment implements IFragmentView {

    public abstract void refreshData();

    public abstract void didLoadData();

    public abstract RecyclerView getRecyclerView();

    abstract int getLayoutId();

    @Override
    public ApplicationComponent getApplicationComponent() {
        return ((BaseActivity) getActivity()).getApplicationComponent();
    }

    public abstract Observable<T> getLoaderData();
}
