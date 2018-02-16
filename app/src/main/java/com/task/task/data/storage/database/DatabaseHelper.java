package com.task.task.data.storage.database;


import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DatabaseHelper {

    Single<List<RestaurantInfo>> getLocalRestaurantData();

    Observable<Boolean> addAllRestaurants(List<RestaurantInfo> listOfRestaurants);
}
