package com.task.task.domain.model;


import android.net.Uri;

public class RestaurantInfo {

    public static final RestaurantInfo EMPTY = new RestaurantInfo("","", 0, 0, Uri.EMPTY, 0);

    public final String name;

    public final String address;

    public final float longitude;

    public final float latitude;

    public final Uri imageUri;

    public final int id;


    public RestaurantInfo(String name, String address, float longitude, float latitude, Uri imageUri, int id) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUri = imageUri;
        this.id = id;
    }

}
