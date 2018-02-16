package com.task.task.data.api.converter;


import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.domain.model.RestaurantInfo;

import java.util.List;


public interface RestaurantsAPIConverter {

    List<RestaurantInfo> convertToRestarauntInfo(List<RestaurantsApiResponse> restaurantsApiResponse);

}
