package lucasbaiao.com.br.moviessearch.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import lucasbaiao.com.br.moviessearch.BuildConfig;
import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDetailDto;
import lucasbaiao.com.br.moviessearch.controller.ApiController;
import lucasbaiao.com.br.moviessearch.controller.FilmDatabaseController;
import lucasbaiao.com.br.moviessearch.database.entity.FilmDetail;
import lucasbaiao.com.br.moviessearch.database.entity.Films;
import lucasbaiao.com.br.moviessearch.exception.InvalidParameterException;

public class FilmDetailsActivity extends AbstractLoaderActivity<Films> {

    private static final String FILM_ID = "film_id";

    @Bind(R.id.originalTitle)
    TextView originalTitle;
    @Bind(R.id.languages)
    TextView languages;
    @Bind(R.id.genres)
    TextView genres;
    @Bind(R.id.production)
    TextView production;
    @Bind(R.id.popularity)
    TextView popularity;

    @Bind(R.id.favorite_check)
    AppCompatCheckBox favoriteCheckBox;
    @Bind(R.id.film_title)
    TextView title;
    @Bind(R.id.referenceDate)
    TextView referenceDate;
    @Bind(R.id.text_ratting)
    TextView rating;
    @Bind(R.id.film_overview)
    TextView overview;
    @Bind(R.id.button_wishlist)
    Button buttonWishlist;
    @Bind(R.id.backdrop_image)
    ImageView backdropImage;
    @Bind(R.id.folder_image)
    ImageView folderImage;

    @Inject
    ApiController controller;
    @Inject
    FilmDatabaseController databaseController;
    
    private long filmId;
    private boolean inBind;

    public static Intent getIntent(Context context, long filmId) {
        Intent intent = new Intent(context, FilmDetailsActivity.class);
        intent.putExtra(FILM_ID, filmId);
        return intent;
    }

    private void toggleFavorite(boolean isFavorite) {
        if (this.inBind) {
            // prevent call checkListener forever
            return;
        }

        this.inBind = true;
        this.databaseController.saveFavorite((int) filmId, isFavorite);
        this.favoriteCheckBox.setChecked(isFavorite);
        this.buttonWishlist.setText(getString(isFavorite ? R.string.del_wishlist : R.string.add_wishlist));
    }

    @OnCheckedChanged(R.id.favorite_check)
    void onFavoriteChanged(android.widget.CompoundButton button, boolean isChecked) {
        this.toggleFavorite(this.favoriteCheckBox.isChecked());
        this.inBind = false;
    }

    @OnClick(R.id.try_again)
    void onTryAgainClick(View view) {
        this.didLoadData();
    }

    @OnClick(R.id.button_wishlist)
    void onFavoriteClick(View view) {
        this.toggleFavorite(!this.favoriteCheckBox.isChecked());
        this.inBind = false;
    }

    @Override
    Observable<Films> getDataLoader() {
        return this.controller.getFilmDetail(this.filmId);
    }

    @Override
    View getMainLayout() {
        return this.findViewById(R.id.header_film_details);
    }

    @Override
    View getErrorLayout() {
        return this.findViewById(R.id.errorLayout);
    }

    @Override
    View getProgressLayout() {
        return this.findViewById(R.id.progressLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_details;
    }

    @Override
    void onDataLoaded(Films value) {
        this.bindViewValues(value);
    }

    @Override
    void showLoading(boolean isVisible) {
        super.showLoading(isVisible);
        ((AppBarLayout) this.findViewById(R.id.app_bar)).setExpanded(!isVisible);
    }

    @Override
    void showError(String error) {
        super.showError(error);
        ((AppBarLayout) this.findViewById(R.id.app_bar)).setExpanded(false);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.getApplicationComponent().inject(this);
        try {
            this.extractParams();
            this.didLoadData();
        } catch (InvalidParameterException ex) {
            Log.e(getClass().getSimpleName(), "Invalid parameter on create film detail activity", ex);
            finish();
        }
    }

    private void bindViewValues(Films film) {
        ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(film.getTitle());
        this.title.setText(film.getTitle());
        this.referenceDate.setText(String.format(Locale.getDefault(), "%s, %s", film.toDateString(), film.getFilmDetail().convertRuntime()));
        this.rating.setText(String.format(Locale.getDefault(), "%.1f", film.getFilmDetail().getVoteAverage()));
        this.overview.setText(film.getFilmDetail().getOverview());

        FilmDetail filmDetail = film.getFilmDetail();
        StringBuilder builder = new StringBuilder();
        for(String value : filmDetail.getSpokenLanguages()) {
            builder.append("\n").append(value);
        }

        this.originalTitle.setText(String.format("Título Original\n%s", film.getOriginalTitle()));
        this.languages.setText(String.format("Idiomas%s", builder.toString()));
        this.popularity.setText(String.format(Locale.getDefault(), "Popularidade\n%.2f", filmDetail.getPopularity()));

        builder = new StringBuilder();
        for(String value : filmDetail.getGenres()) {
            builder.append("\n").append(value);
        }
        this.genres.setText(String.format("Gênero%s", builder.toString()));

        builder = new StringBuilder();
        for(String value : filmDetail.getProductionCompanies()) {
            builder.append("\n").append(value);
        }
        this.production.setText(String.format("Produção%s", builder.toString()));

        this.favoriteCheckBox.setChecked(film.isFavorite());
        this.buttonWishlist.setText(getString(film.isFavorite() ? R.string.del_wishlist : R.string.add_wishlist));

        this.loadImage(this.backdropImage, film.getBackdropPath());
        this.loadImage(this.folderImage, film.getPosterPath());
    }

    private void loadImage(final ImageView imageView, String path) {
            //final ProgressBar progressView = (ProgressBar) mView.findViewById(R.id.progress);
            //progressView.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(String.format("%s/original/%s", BuildConfig.IMAGE_BASE_URL, path))
                    .centerInside()
                    .error(R.drawable.ic_no_image)
                    .fit()
                    //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    //.networkPolicy(NetworkPolicy.NO_CACHE)
                    //.onlyScaleDown()
                    //.resize(250, 200)
                    //.rotate(90)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });
    }

    private void extractParams() throws InvalidParameterException {
        this.filmId = getIntent().getExtras().getLong(FILM_ID, 0);
        if (filmId <= 0) {
            throw new InvalidParameterException("FilmDto ID");
        }
    }
}
