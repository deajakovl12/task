package com.task.task.domain.usecase;


import android.database.sqlite.SQLiteDatabase;

import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.data.service.NetworkService;
import com.task.task.data.storage.TaskPreferences;
import com.task.task.data.storage.database.DatabaseHelper;
import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RestaurantUseCaseImpl implements RestaurantUseCase {

    private final NetworkService networkService;

    private final TaskPreferences preferences;

    private final DatabaseHelper databaseHelper;


    public RestaurantUseCaseImpl(NetworkService networkService, TaskPreferences preferences, DatabaseHelper databaseHelper) {
        this.networkService = networkService;
        this.preferences = preferences;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public Single<List<RestaurantsApiResponse>> getRestaurantInfo() {
        return Single
                .defer(() -> networkService.restaurantInfo());
    }

    @Override
    public Completable addAllRestaurants(List<RestaurantInfo> listOfRestaurants) {
        return Completable
                .defer(() -> databaseHelper.addAllRestaurants(listOfRestaurants));
    }

    @Override
    public Single<List<RestaurantInfo>> getLocalRestaurantData() {
        return Single.defer(() -> databaseHelper.getLocalRestaurantData());
    }

    @Override
    public Completable deleteRestaurant(int restaurantId) {
        return Completable.defer(() -> databaseHelper.deleteRestaurant(restaurantId));
    }

    @Override
    public Completable updateRestaurantData(RestaurantInfo restaurantInfo) {
        return Completable.defer(() -> databaseHelper.updateRestaurantData(restaurantInfo));
    }

    @Override
    public Completable addNewRestaurant(RestaurantInfo restaurantInfo) {
        return Completable.defer(() -> databaseHelper.addNewRestaurant(restaurantInfo));
    }
}
