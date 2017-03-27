package lucasbaiao.com.br.moviessearch.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.util.Log;

public class ConnectionHelper {

    private ConnectionHelper() {
        //statics only
    }

    public static boolean hasInternet(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connected = connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            Log.d("connection status", e.getMessage());
        }

        return connected;
    }

    public static boolean verifyLocation(LocationManager locationManager){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
