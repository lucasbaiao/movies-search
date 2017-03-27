package lucasbaiao.com.br.moviessearch;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class HttpRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();

        logRequest(chain, request);
        logBody(request);

        Response response = chain.proceed(request);
        logResponse(t1, response);

        return chain.proceed(addApiKeyQuery(chain).build());
    }

    private Request.Builder addApiKeyQuery(Chain chain) {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.ApiKey)
                .build();

        // add request query api key param
        return original.newBuilder().url(url);
    }

    private void logBody(Request request) throws IOException {
        if (request.body() != null) {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            Log.d("BODY: ", String.format("Sending body: %s",
                    buffer.readUtf8()));
        }
    }

    private void logRequest(Chain chain, Request request) {
        Log.d("REQUEST", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
    }

    private void logResponse(long t1, Response response) {
        long t2 = System.nanoTime();
        Log.d("RESPONSE", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
    }
}
