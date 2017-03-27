package lucasbaiao.com.br.moviessearch.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lucasbaiao.com.br.moviessearch.R;

import java.util.List;

public abstract class PagerAdapter<T, H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ProgressViewType = 1;
    public static final int MovieViewType = 2;

    private final List<T> mValues;
    private OnLoadMoreListener loadMoreListener;
    private boolean isLoading = false;
    private boolean isMoreDataAvailable = true;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    protected abstract H getViewHolder(View view);

    protected abstract void bindViewValues(H holder, T t);

    /** notifyDataSetChanged is final method so we can't override it
     * call adapter.notifyDataChanged(); after update the list
     */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }

    public void removeAt(int position) {
        mValues.remove(position);
        notifyDataChanged();
    }

    public PagerAdapter(List<T> items, OnLoadMoreListener listener) {
        this.mValues = items;
        this.loadMoreListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ProgressViewType) {
            return new LoadHolder(inflater.inflate(R.layout.progress_load_item,parent,false));
        } else {
            View view = inflater.inflate(R.layout.fragment_film_item, parent, false);
            return this.getViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(position >= getItemCount() -1 && this.isMoreDataAvailable && !this.isLoading && this.loadMoreListener!=null){
            this.isLoading = true;
            this.loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == MovieViewType) {
            this.bindViewValues((H) holder, this.mValues.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(this.mValues.get(position) != null){
            return MovieViewType;
        } else {
            return ProgressViewType;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private static class LoadHolder extends RecyclerView.ViewHolder{
        LoadHolder(View itemView) {
            super(itemView);
        }
    }
}
