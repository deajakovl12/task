package com.task.task.data.api.converter;


import android.net.Uri;

import com.task.task.data.api.models.response.RestaurantsApiResponse;
import com.task.task.data.storage.PreferenceRepository;
import com.task.task.domain.model.RestaurantInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.task.task.utils.StringNumberUtils.itOrDefault;


public class RestaurantsAPIConverterImpl implements RestaurantsAPIConverter {

    private static final long EMPTY = 0;

    private static final String EMPTY_STRING = "";

    PreferenceRepository preferenceRepository;


    public RestaurantsAPIConverterImpl(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public List<RestaurantInfo> convertToRestarauntInfo(final List<RestaurantsApiResponse> restaurantsApiResponse) {

        if(restaurantsApiResponse == null){
            return new ArrayList<>();
        }

        List<RestaurantInfo> restaurantInfoList = new ArrayList<>(restaurantsApiResponse.size());

        int id = preferenceRepository.getLastRestaurantId();

        Collections.reverse(restaurantsApiResponse);

        for (RestaurantsApiResponse apiResponse : restaurantsApiResponse) {
            if (apiResponse == null) {
                restaurantInfoList.add(RestaurantInfo.EMPTY);
            } else {
                restaurantInfoList.add(new RestaurantInfo(
                        itOrDefault(apiResponse.name, EMPTY_STRING),
                        itOrDefault(apiResponse.address, EMPTY_STRING),
                        apiResponse.longitude,
                        apiResponse.latitude,
                        Uri.EMPTY,
                        restaurantInfoList.size() + 1 + id));
            }
        }

        return restaurantInfoList;
    }
}
