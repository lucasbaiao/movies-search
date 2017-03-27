package lucasbaiao.com.br.moviessearch.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import lucasbaiao.com.br.moviessearch.AppApplication;
import lucasbaiao.com.br.moviessearch.R;
import lucasbaiao.com.br.moviessearch.di.component.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void onViewReady(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
        onViewReady(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AppApplication) this.getApplication()).getComponent();
    }

    @Override
    public void setTitle(CharSequence title) {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        this.setTitle(getString(titleId));
    }

    protected Toolbar setupToolbar() {
        return this.setupToolbar(R.id.toolbar);
    }

    protected Toolbar setupToolbar(@IdRes int toolbarId) {
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);
        initToolbar(true);
        return toolbar;
    }

    private void initToolbar(boolean enabled) {
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(!enabled);
        getSupportActionBar().setHomeButtonEnabled(enabled);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        getSupportActionBar().setLogo(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
    }

    protected void enableBackButton(boolean enabled) {
        this.initToolbar(enabled);
    }
}