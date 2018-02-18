package com.task.task.utils;

import com.google.android.gms.location.LocationRequest;


public class LocationHelper {

    private static final int UPDATE_INTERVAL = 4000;
    private static final int FASTEST_UPDATE_INTERVAL = 2000;

    public static LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}