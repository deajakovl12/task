package com.task.task.ui.details;


import com.task.task.ui.home.HomeView;

public interface RestaurantDetailsPresenter {

    void setView(RestaurantDetailsView view);

    void getRestaurants();

    void dispose();
}
