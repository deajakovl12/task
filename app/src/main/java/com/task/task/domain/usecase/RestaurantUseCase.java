package com.task.task.domain.usecase;


import com.task.task.data.api.models.response.RestaurantsApiResponse;

import java.util.List;

import io.reactivex.Single;

public interface RestaurantUseCase {

    Single<List<RestaurantsApiResponse>> getRestaurantInfo();

}
