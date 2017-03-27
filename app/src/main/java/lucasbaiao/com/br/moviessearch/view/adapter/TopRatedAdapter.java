package lucasbaiao.com.br.moviessearch.view.adapter;


import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lucasbaiao.com.br.moviessearch.BuildConfig;
import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.database.entity.Films;
import lucasbaiao.com.br.moviessearch.view.fragment.PagerLoaderFragment;

public class TopRatedAdapter extends PagerAdapter<Films, TopRatedAdapter.ViewHolder> {

    private PagerLoaderFragment.OnListFragmentInteractionListener mListener;

    public TopRatedAdapter(List<Films> items, OnLoadMoreListener listener, PagerLoaderFragment.OnListFragmentInteractionListener itemListener) {
        super(items, listener);
        this.mListener = itemListener;
    }

    void bindData(ViewHolder holder, final Films filmDto) {
        holder.item = filmDto;
        holder.title.setText(filmDto.getTitle());
        holder.releaseDate.setText(filmDto.toDateString());
        holder.inBind(true);
        holder.favoriteCheckBox.setChecked(filmDto.isFavorite());
        holder.inBind(false);
        this.loadImage(holder, filmDto);
    }

    void loadImage(ViewHolder holder, final Films filmDto) {
        final ProgressBar progressView = (ProgressBar) holder.rootView.findViewById(R.id.progress);
        progressView.setVisibility(View.VISIBLE);
        Picasso.with(holder.rootView.getContext())
                .load(String.format("%s/original/%s", BuildConfig.IMAGE_BASE_URL, filmDto.getPosterPath()))
                .centerInside()
                .error(R.drawable.ic_no_image)
                .fit()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }


    @Override
    protected ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindViewValues(ViewHolder holder, Films filmDto) {
        this.bindData(holder, filmDto);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

        @Bind(R.id.folder)
        ImageView imageView;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.referenceDate)
        TextView releaseDate;
        @Bind(R.id.favorite_check)
        AppCompatCheckBox favoriteCheckBox;

        View rootView;
        Films item;
        private boolean inBind;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.rootView = view;
            this.rootView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.window_background));
            this.favoriteCheckBox.setOnCheckedChangeListener(this);
            this.rootView.setOnClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!inBind && mListener != null && this.item != null) {
                final boolean value = buttonView.isChecked();
                mListener.onFavoriteSelected((int) this.item.getId(), value);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        item.setFavorite(value);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            if (mListener != null && this.item != null) {
                mListener.onItemSelected((int) this.item.getId(), this.imageView);
            }
        }

        public void inBind(boolean inBind) {
            this.inBind = inBind;
        }
    }
}
