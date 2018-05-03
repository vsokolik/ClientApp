package ru.beetlesoft.clientapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class LocationUtils {

    private static Location getLocation(Context context){
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (lm != null) {

                boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(isGPSEnabled){
                    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if(isNetworkEnabled){
                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }


            }
        }

        return location;
    }

    public static double getLongitude(Context context) {
        Location location = getLocation(context);
        if (location!=null) {
            return location.getLongitude();
        } else {
            return 0;
        }
    }

    public static double getLatitude(Context context){
        Location location = getLocation(context);
        if (location!=null) {
            return location.getLatitude();
        } else {
            return 0;
        }
    }
}
