package com.task.task.domain.usecase;


import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.data.service.NetworkService;
import com.task.task.data.storage.TemplatePreferences;

import java.util.List;

import io.reactivex.Single;

public class RestaurantUseCaseImpl implements RestaurantUseCase {

    private final NetworkService networkService;

    private final TemplatePreferences preferences;


    public RestaurantUseCaseImpl(NetworkService networkService, TemplatePreferences preferences) {
        this.networkService = networkService;
        this.preferences = preferences;
    }

    @Override
    public Single<List<RestaurantsApiResponse>> getRestaurantInfo() {
        return Single
                .defer(() -> networkService.restaurantInfo());
    }

}
