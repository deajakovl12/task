package com.task.task.ui.home;

import com.task.task.domain.model.RestaurantInfo;

import java.util.List;


public interface HomeView {

    void showData(List<RestaurantInfo> restaurantInfo);

    void restaurantDeleted();
}
