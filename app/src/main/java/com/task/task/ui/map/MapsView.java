package com.task.task.ui.map;


import com.task.task.domain.model.RestaurantInfo;

import java.util.List;

public interface MapsView {
    void showData(List<RestaurantInfo> restaurantInfo);
}
