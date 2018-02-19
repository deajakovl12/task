package com.task.task.ui.map;


import com.task.task.domain.model.RestaurantInfo;

public interface MapsRouter {
    void onRestaurantDetailsOrAddNew(RestaurantInfo restaurantInfo);
}
