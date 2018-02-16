package com.task.task.data.storage.database;


import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

import io.reactivex.Observable;

public interface DatabaseHelper {

    Observable<Boolean> addAllRestaurants(List<RestaurantInfo> listOfRestaurants);
}
