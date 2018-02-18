package com.task.task.ui.details;


import com.task.task.domain.model.RestaurantInfo;

public interface RestaurantDetailsPresenter {

    void setView(RestaurantDetailsView view);

    void updateRestaurantData(RestaurantInfo restaurantInfo);

    void dispose();
}
