package com.task.task.data.storage.database;


public class RestaurantContract {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
                    RestaurantEntry.RESTAURANT_ID + " INTEGER PRIMARY KEY," +
                    RestaurantEntry.RESTAURANT_NAME + " TEXT," +
                    RestaurantEntry.RESTAURANT_ADDRESS + " TEXT," +
                    RestaurantEntry.RESTAURANT_LONGITUDE + " REAL, " +
                    RestaurantEntry.RESTAURANT_LATITUDE + " REAL, " +
                    RestaurantEntry.RESTAURANT_URI + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RestaurantEntry.TABLE_NAME;

    private RestaurantContract() {
    }

    public static class RestaurantEntry {
        public static final String TABLE_NAME = "restaurants";
        public static final String RESTAURANT_NAME = "restaurant_name";
        public static final String RESTAURANT_ADDRESS = "restaurant_address";
        public static final String RESTAURANT_LONGITUDE = "restaurant_longitude";
        public static final String RESTAURANT_LATITUDE = "restaurant_latitude";
        public static final String RESTAURANT_URI = "restaurant_uri";
        public static final String RESTAURANT_ID = "restaurant_id";

    }
}
