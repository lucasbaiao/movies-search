package lucasbaiao.com.br.moviessearch.view.adapter;


import java.util.List;

import lucasbaiao.com.br.moviessearch.database.entity.Films;
import lucasbaiao.com.br.moviessearch.view.fragment.PagerLoaderFragment;

public class PopularAdapter extends TopRatedAdapter {

    public PopularAdapter(List<Films> items, OnLoadMoreListener listener, PagerLoaderFragment.OnListFragmentInteractionListener itemListener) {
        super(items, listener, itemListener);
    }
}
