package com.task.task.data.storage.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.task.task.data.storage.PreferenceRepository;
import com.task.task.domain.model.RestaurantInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {

    protected Context context;
    private PreferenceRepository preferenceRepository;

    public DatabaseHelperImpl(final Context context, final String databaseName, final int databaseVersion,
                              final PreferenceRepository preferenceRepository) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RestaurantContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(RestaurantContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    @Override
    public Completable addAllRestaurants(List<RestaurantInfo> listOfRestaurants) {
        return Completable.defer(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            int lastId = 0;
            try {
                ContentValues values = new ContentValues();
                for (RestaurantInfo restaurantInfo : listOfRestaurants) {
                    lastId = restaurantInfo.id + 1;
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ID, restaurantInfo.id);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_NAME, restaurantInfo.name);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ADDRESS, restaurantInfo.address);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_LONGITUDE, restaurantInfo.longitude);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_LATITUDE, restaurantInfo.latitude);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_URI, String.valueOf(restaurantInfo.imageUri));
                    db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
                }
                preferenceRepository.setLastRestaurantId(lastId);
                db.setTransactionSuccessful();

            } finally {
                db.endTransaction();
            }
            return Completable.complete();
        });
    }

    @Override
    public Single<List<RestaurantInfo>> getLocalRestaurantData() {
        return Single.defer(() -> {
            SQLiteDatabase db = getReadableDatabase();

            String[] projection = {
                    RestaurantContract.RestaurantEntry.RESTAURANT_ID,
                    RestaurantContract.RestaurantEntry.RESTAURANT_NAME,
                    RestaurantContract.RestaurantEntry.RESTAURANT_ADDRESS,
                    RestaurantContract.RestaurantEntry.RESTAURANT_LONGITUDE,
                    RestaurantContract.RestaurantEntry.RESTAURANT_LATITUDE,
                    RestaurantContract.RestaurantEntry.RESTAURANT_URI
            };
            Cursor cursor;
            cursor = db.query(
                    RestaurantContract.RestaurantEntry.TABLE_NAME, projection, null, null, null, null, null);

            List<RestaurantInfo> restaurantInfoList = new ArrayList<>();
            while (cursor.moveToNext()) {
                RestaurantInfo restaurantInfo = new RestaurantInfo(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3),
                        cursor.getFloat(4),
                        Uri.parse(cursor.getString(5)),
                        cursor.getInt(0));
                restaurantInfoList.add(restaurantInfo);
            }
            cursor.close();
            Collections.reverse(restaurantInfoList);
            return Single.just(restaurantInfoList);
        });
    }

    @Override
    public Completable deleteRestaurant(int restaurantId) {
        return Completable.defer(() -> {
            SQLiteDatabase db = getReadableDatabase();
            String selection = RestaurantContract.RestaurantEntry.RESTAURANT_ID + "=?";
            String[] selectionArgs = {String.valueOf(restaurantId)};
            db.delete(RestaurantContract.RestaurantEntry.TABLE_NAME, selection, selectionArgs);

            return Completable.complete();
        });

    }

    @Override
    public Completable updateRestaurantData(RestaurantInfo restaurantInfo) {
        return Completable.defer(() -> {

            ContentValues values = new ContentValues();
            SQLiteDatabase db = getReadableDatabase();
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_NAME, restaurantInfo.name);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ADDRESS, restaurantInfo.address);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_URI, String.valueOf(restaurantInfo.imageUri));
            String selection = RestaurantContract.RestaurantEntry.RESTAURANT_ID + "=?";
            String[] selectionArgs = {String.valueOf(restaurantInfo.id)};
            db.update(RestaurantContract.RestaurantEntry.TABLE_NAME, values, selection, selectionArgs);

            return Completable.complete();
        });
    }

    @Override
    public Completable addNewRestaurant(RestaurantInfo restaurantInfo) {
        return Completable.defer(() -> {
            ContentValues values = new ContentValues();
            SQLiteDatabase db = getReadableDatabase();
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ID, restaurantInfo.id);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_NAME, restaurantInfo.name);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ADDRESS, restaurantInfo.address);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_LATITUDE, restaurantInfo.latitude);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_LONGITUDE, restaurantInfo.longitude);
            values.put(RestaurantContract.RestaurantEntry.RESTAURANT_URI, String.valueOf(restaurantInfo.imageUri));
            db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
            preferenceRepository.setLastRestaurantId(preferenceRepository.getLastRestaurantId() + 1);
            return Completable.complete();
        });
    }
}
