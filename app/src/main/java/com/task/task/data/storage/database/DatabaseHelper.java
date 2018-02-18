package com.task.task.data.storage.database;


import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

public interface DatabaseHelper {

    Single<List<RestaurantInfo>> getLocalRestaurantData();

    Observable<Boolean> addAllRestaurants(List<RestaurantInfo> listOfRestaurants);

    Observable<Boolean> deleteRestaurant(int restaurantId);

    ObservableSource<Boolean> updateRestaurantData(RestaurantInfo restaurantInfo);

    Completable addNewRestaurant(RestaurantInfo restaurantInfo);
}
