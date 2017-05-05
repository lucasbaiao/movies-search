package lucasbaiao.com.br.moviessearch.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import lucasbaiao.com.br.moviessearch.api.ApiMovieRouter;
import lucasbaiao.com.br.moviessearch.BuildConfig;
import lucasbaiao.com.br.moviessearch.HttpRequestInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private ApiMovieRouter apiMovieRouter;

    @Provides
    ApiMovieRouter apiMovieRouter() {
        return this.apiMovieRouter;
    }

    public ApiModule(Context context) {
        this.buildEndpoints(context);
    }

    private void buildEndpoints(Context context) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(BuildConfig.ConnectTimeout, TimeUnit.SECONDS)
                        .writeTimeout(BuildConfig.WriteTimeout, TimeUnit.SECONDS)
                        .readTimeout(BuildConfig.ReadTimeout, TimeUnit.SECONDS)
                        .hostnameVerifier(getHostnameVerifier())
                        .sslSocketFactory(getSslContext())
                        .addInterceptor(new HttpRequestInterceptor())
                        .build();
                initRests(okHttpClient, context);
            } catch (Exception ex) {
                Log.e(getClass().getSimpleName(), ex.getMessage(), ex);
            }
    }

    private void initRests(OkHttpClient okHttpClient, Context context) {
        Log.d(this.getClass().getSimpleName(), "Creating endpoints...");
        Log.d(this.getClass().getSimpleName(), String.format("BASE URL: %s", BuildConfig.BASE_URL));
        this.apiMovieRouter = getRxRetrofitClient(BuildConfig.BASE_URL, okHttpClient).create(ApiMovieRouter.class);
    }

    @NonNull
    private SSLSocketFactory getSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, getTrustManagers(), new java.security.SecureRandom());
        return sslContext.getSocketFactory();
    }

    @NonNull
    private TrustManager[] getTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
    }

    @NonNull
    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    @NonNull
    private Retrofit getRxRetrofitClient(String url, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @NonNull
    private Retrofit getSimpleRetrofitClient(String url, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}