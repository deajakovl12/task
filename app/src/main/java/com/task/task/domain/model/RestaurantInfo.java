package com.task.task.domain.model;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantInfo implements Parcelable{

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeFloat(this.longitude);
        dest.writeFloat(this.latitude);
        dest.writeParcelable(this.imageUri, flags);
        dest.writeInt(this.id);
    }

    protected RestaurantInfo(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.longitude = in.readFloat();
        this.latitude = in.readFloat();
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());
        this.id = in.readInt();
    }

    public static final Creator<RestaurantInfo> CREATOR = new Creator<RestaurantInfo>() {
        @Override
        public RestaurantInfo createFromParcel(Parcel source) {
            return new RestaurantInfo(source);
        }

        @Override
        public RestaurantInfo[] newArray(int size) {
            return new RestaurantInfo[size];
        }
    };
}
