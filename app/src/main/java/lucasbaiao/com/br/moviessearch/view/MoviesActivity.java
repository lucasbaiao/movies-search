package lucasbaiao.com.br.moviessearch.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.ImageView;

import javax.inject.Inject;

import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.controller.FilmDatabaseController;
import lucasbaiao.com.br.moviessearch.view.fragment.BaseLoaderFragment;
import lucasbaiao.com.br.moviessearch.view.fragment.FavoritesFragment;
import lucasbaiao.com.br.moviessearch.view.fragment.PagerLoaderFragment;
import lucasbaiao.com.br.moviessearch.view.fragment.PopularFragment;
import lucasbaiao.com.br.moviessearch.view.fragment.TopRatedFragment;

public class MoviesActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        PagerLoaderFragment.OnListFragmentInteractionListener {

    private Fragment popularFragment = null;
    private Fragment topRatedFragment = null;
    private Fragment favoriteFragment = null;
    private Fragment selectedFragment = null;


    @Inject
    FilmDatabaseController filmController;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.getApplicationComponent().inject(this);
        super.enableBackButton(false);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        this.toggleMenu(R.id.popular_films);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (this.selectedFragment != null && this.selectedFragment instanceof BaseLoaderFragment) {
            ((BaseLoaderFragment) this.selectedFragment).refreshData();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.toggleMenu(item.getItemId());
        return true;
    }

    @Override
    public void onItemSelected(int filmId, ImageView imageView) {
        Intent intent = FilmDetailsActivity.getIntent(this, filmId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String transitionName = getString(R.string.folder_transition);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, imageView, transitionName);
            startActivityForResult(intent, 10, transitionActivityOptions.toBundle());
        } else {
            startActivityForResult(intent, 10);
        }
    }

    @Override
    public void onFavoriteSelected(int filmId, boolean isChecked) {
        this.filmController.saveFavorite(filmId, isChecked);
    }

    @Override
    public void makeSnackBar(String message) {
        Snackbar.make(this.findViewById(R.id.root), message, Snackbar.LENGTH_LONG).show();
    }

    private void toggleMenu(int itemId) {
        switch (itemId){
            case R.id.popular_films:
                if (popularFragment == null) {
                    popularFragment= PopularFragment.newInstance();
                }
                selectedFragment = popularFragment;
                break;
            case R.id.tap_rated_films:
                if (topRatedFragment == null) {
                    topRatedFragment = TopRatedFragment.newInstance();
                }
                selectedFragment = topRatedFragment;
                break;
            case R.id.favorite_films:
                if (favoriteFragment == null) {
                    favoriteFragment = FavoritesFragment.newInstance();
                }
                selectedFragment = favoriteFragment;
                break;
        }

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, selectedFragment).commit();
    }
}
