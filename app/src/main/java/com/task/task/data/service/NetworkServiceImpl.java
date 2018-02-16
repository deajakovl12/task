package com.task.task.data.service;


import com.task.task.data.api.models.response.RestaurantsApiResponse;

import java.util.List;

import io.reactivex.Single;

public final class NetworkServiceImpl implements NetworkService {

    private final TaskAPI taskAPI;

    public NetworkServiceImpl(final TaskAPI taskAPI) {
        this.taskAPI = taskAPI;
    }


    @Override
    public Single<List<RestaurantsApiResponse>> restaurantInfo() {
        return Single.defer(() -> taskAPI.restaurantInfo());
    }


}
