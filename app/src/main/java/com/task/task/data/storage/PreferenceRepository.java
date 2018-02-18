package com.task.task.data.storage;

public interface PreferenceRepository {

    void setDataDownoladed();

    boolean getDataDownloaded();

    void setLastRestaurantId(int restaurantId);

    int getLastRestaurantId();

}
