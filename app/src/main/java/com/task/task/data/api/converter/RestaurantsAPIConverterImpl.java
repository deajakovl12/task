package com.task.task.data.api.converter;


import android.net.Uri;

import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.domain.model.RestaurantInfo;

import java.util.ArrayList;
import java.util.List;


public class RestaurantsAPIConverterImpl implements RestaurantsAPIConverter {

    @Override
    public List<RestaurantInfo> convertToRestarauntInfo(final List<RestaurantsApiResponse> restaurantsApiResponse) {

        List<RestaurantInfo> restaurantInfoList = new ArrayList<>(restaurantsApiResponse.size());

        for (RestaurantsApiResponse apiResponse : restaurantsApiResponse) {
            if (apiResponse == null) {
                restaurantInfoList.add(RestaurantInfo.EMPTY);
            } else {
                restaurantInfoList.add(new RestaurantInfo(
                        apiResponse.name,
                        apiResponse.address,
                        apiResponse.longitude,
                        apiResponse.latitude,
                        Uri.EMPTY,
                        restaurantInfoList.size() + 1));
            }
        }
        return restaurantInfoList;
    }
}
