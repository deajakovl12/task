package com.task.task.data.api.models.response;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class RestaurantsApiResponse {

    @SerializedName("Name")
    public final String name;

    @SerializedName("Address")
    public final String address;

    @SerializedName("Longitude")
    public final float longitude;

    @SerializedName("Latitude")
    public final float latitude;


    public RestaurantsApiResponse(String name, String address, float longitude, float latitude) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}



