package lucasbaiao.com.br.moviessearch.view;

import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lucasbaiao.com.br.moviessearch.R;

public abstract class AbstractLoaderActivity<T> extends BaseActivity {

    abstract void onDataLoaded(T value);
    abstract View getProgressLayout();
    abstract View getMainLayout();
    abstract View getErrorLayout();
    abstract Observable<T> getDataLoader();

    void didLoadData() {
        this.showLoading(true);
        this.getDataLoader()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DataLoader());
    }

    void showLoading(boolean isVisible) {
        this.getProgressLayout().setVisibility(isVisible ? View.VISIBLE : View.GONE);
        this.getMainLayout().setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    void showError(Throwable error) {
        this.showError(error.getMessage());
    }

    void showError(String error) {
        this.showLoading(false);
        this.getMainLayout().setVisibility(View.GONE);
        this.getErrorLayout().setVisibility(View.VISIBLE);
        ((TextView) this.getErrorLayout().findViewById(R.id.errorMessage)).setText(error);
    }

    private class DataLoader implements io.reactivex.Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onError(Throwable e) {
            AbstractLoaderActivity.this.showError(e);
        }

        @Override
        public void onComplete() {
            AbstractLoaderActivity.this.showLoading(false);
        }

        @Override
        public void onNext(T value) {
            AbstractLoaderActivity.this.onDataLoaded(value);
        }
    }
}
