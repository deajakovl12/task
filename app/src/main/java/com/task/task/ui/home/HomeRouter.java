package com.task.task.ui.home;

import com.task.task.domain.model.RestaurantInfo;

public interface HomeRouter {

    void onRestaurantDetailsOrAddNew(RestaurantInfo restaurantInfo, String addOrEdit);

    void onMap();
}
