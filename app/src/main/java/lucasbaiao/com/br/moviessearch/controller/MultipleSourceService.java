package lucasbaiao.com.br.moviessearch.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import lucasbaiao.com.br.moviessearch.util.ConnectionHelper;

public final class MultipleSourceService {

    public interface InnerService<T> {

        boolean localService(InnerServiceContext<T> context);

        boolean remoteService(InnerServiceContext<T> context);

        void onNoRemoteCommunication(InnerServiceContext<T> context);

    }

    public static class InnerServiceContext<T> {

        private T localServiceData;
        private T remoteServiceData;

        private InnerServiceContext() {

        }

        public void putLocalData(T localData) {
            localServiceData = localData;
        }

        public void putRemoteData(T remoteData) {
            remoteServiceData = remoteData;
        }

        public T getLocalServiceData() {
            return localServiceData;
        }

        public T getRemoteServiceData() {
            return remoteServiceData;
        }
    }


    private MultipleSourceService() {
        //statics only
    }

    @NonNull
    public static <T> Observable<T> buildExecutor(final Context context, final InnerService<T> service) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                try {

                    InnerServiceContext<T> innerContext = new InnerServiceContext<>();
                    boolean callNext = service.localService(innerContext);

                    if (innerContext.getLocalServiceData() != null) {
                        subscriber.onNext(innerContext.getLocalServiceData());
                    }

                    if (callNext) {
                        if (ConnectionHelper.hasInternet(context)) {
                            service.remoteService(innerContext);
                        } else {
                            service.onNoRemoteCommunication(innerContext);
                        }
                        subscriber.onNext(innerContext.getRemoteServiceData());
                    }

                } catch (Throwable e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onComplete();
                }
            }
        });
    }
}
