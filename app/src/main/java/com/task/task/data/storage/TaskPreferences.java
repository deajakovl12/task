package com.task.task.data.storage;

import android.content.SharedPreferences;

public final class TaskPreferences implements PreferenceRepository {

    private static final String KEY_DATA_DOWNLOADED = "key_data_downloaded";

    private static final String KEY_LAST_RESTAURANT_ID = "key_last_restaurant_id";

    private final SharedPreferences secureDelegate;


    public static TaskPreferences create(final SharedPreferences secureDelegate) {
        return new TaskPreferences(secureDelegate);
    }

    private TaskPreferences(final SharedPreferences secureDelegate) {
        this.secureDelegate = secureDelegate;
    }

    @Override
    public void setDataDownoladed() {
        secureDelegate.edit().putBoolean(KEY_DATA_DOWNLOADED, true).apply();
    }

    @Override
    public boolean getDataDownloaded() {
        return secureDelegate.getBoolean(KEY_DATA_DOWNLOADED, false);
    }

    @Override
    public void setLastRestaurantId(int restaurantId) {
        secureDelegate.edit().putInt(KEY_LAST_RESTAURANT_ID, restaurantId).apply();

    }

    @Override
    public int getLastRestaurantId() {
        return secureDelegate.getInt(KEY_LAST_RESTAURANT_ID, 0);
    }
}
