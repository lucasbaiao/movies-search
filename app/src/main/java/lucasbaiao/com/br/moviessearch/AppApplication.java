package lucasbaiao.com.br.moviessearch;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import lucasbaiao.com.br.moviessearch.di.component.ApiComponent;
import lucasbaiao.com.br.moviessearch.di.component.ApplicationComponent;
import lucasbaiao.com.br.moviessearch.di.component.DaggerApiComponent;
import lucasbaiao.com.br.moviessearch.di.component.DaggerApplicationComponent;
import lucasbaiao.com.br.moviessearch.di.module.ApiModule;
import lucasbaiao.com.br.moviessearch.di.module.ApplicationModule;

public class AppApplication extends Application {
    
    private ApiComponent apiComponent;
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.startPaperDb();
        this.startFabric();
        this.initComponent();
    }

    private void startPaperDb() {
        Paper.init(this);
    }

    private void startFabric() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void initComponent() {
        this.apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule(this))
                .build();
        this.component = DaggerApplicationComponent.builder()
                .apiComponent(apiComponent)
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
