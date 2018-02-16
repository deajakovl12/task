package com.task.task.data.service;

import com.task.task.data.api.models.response.RestaurantsApiResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

import static com.task.task.data.api.APIConstants.PATH_RESTAURANTS;


public interface TaskAPI {


    @GET(PATH_RESTAURANTS)
    Single<List<RestaurantsApiResponse>> restaurantInfo();

}
