package com.task.task.ui.details;


import com.task.task.domain.model.RestaurantInfo;

public interface RestaurantDetailsAddNewPresenter {

    void setView(RestaurantDetailsAddNewView view);

    void updateRestaurantData(RestaurantInfo restaurantInfo);

    void dispose();

    void addNewRestaurant(RestaurantInfo restaurantInfo);

    int getLastRestaurantId();

}
