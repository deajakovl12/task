package com.task.task.data.storage.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.task.task.domain.model.RestaurantInfo;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;

public class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {

    protected Context context;

    public DatabaseHelperImpl(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
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
    public Observable<Boolean> addAllRestaurants(List<RestaurantInfo> listOfRestaurants) {
        return Observable.defer(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (RestaurantInfo restaurantInfo : listOfRestaurants) {
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ID, restaurantInfo.id);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_NAME, restaurantInfo.name);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_ADDRESS, restaurantInfo.address);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_LONGITUDE, restaurantInfo.longitude);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_LATITUDE, restaurantInfo.latitude);
                    values.put(RestaurantContract.RestaurantEntry.RESTAURANT_URI, String.valueOf(restaurantInfo.imageUri));
                    db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
                }
                db.setTransactionSuccessful();

            }
            catch (Exception e){
                return Observable.just(false);
            }
            finally {
                db.endTransaction();
            }
            return Observable.just(true);
        });
    }


}
