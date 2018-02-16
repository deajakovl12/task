package com.task.task.manager;


import android.net.ConnectivityManager;

public class NetworkManagerImpl implements NetworkManager {

    private final ConnectivityManager connectivityManager;

    public NetworkManagerImpl(final ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @Override
    public boolean isConnected() {
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    @Override
    public boolean isConnectedWifi() {
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
            return true;
        } else {
            return false;
        }
    }
}
