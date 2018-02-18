package com.task.task.domain.usecase;


import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface RestaurantUseCase {

    Single<List<RestaurantsApiResponse>> getRestaurantInfo();

    Completable addAllRestaurants(List<RestaurantInfo> listOfRestaurants);

    Single<List<RestaurantInfo>> getLocalRestaurantData();

    Completable deleteRestaurant(int restaurantId);

    Completable updateRestaurantData(RestaurantInfo restaurantInfo);

    Completable addNewRestaurant(RestaurantInfo restaurantInfo);
}
