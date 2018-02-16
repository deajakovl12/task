package com.task.task.domain.usecase;


import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface RestaurantUseCase {

    Single<List<RestaurantsApiResponse>> getRestaurantInfo();

    Observable<Boolean> addAllRestaurants(List<RestaurantInfo> listOfRestaurants);

}
