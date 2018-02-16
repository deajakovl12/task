package com.task.task.data.service;


import com.task.task.data.api.models.response.RestaurantsApiResponse;

import java.util.List;

import io.reactivex.Single;

public interface NetworkService {

    Single<List<RestaurantsApiResponse>> restaurantInfo();
}
