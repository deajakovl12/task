package com.task.task.ui.home;

import android.app.Activity;

import com.task.task.domain.model.RestaurantInfo;
import com.task.task.ui.details.RestaurantDetailsActivity;

public class HomeRouterImpl implements HomeRouter {

    private final Activity activity;

    public HomeRouterImpl(final Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onRestaurantDetails(RestaurantInfo restaurantInfo) {
        activity.startActivity(RestaurantDetailsActivity.createIntent(activity, restaurantInfo));
    }
}
