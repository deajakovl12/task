package com.task.task.ui.home;

import android.app.Activity;

import com.task.task.domain.model.RestaurantInfo;
import com.task.task.ui.details.RestaurantDetailsActivity;

import static com.task.task.utils.Constants.HomeActivityConstants.UPDATE_RESTAURANT_CODE;

public class HomeRouterImpl implements HomeRouter {

    private final Activity activity;

    public HomeRouterImpl(final Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onRestaurantDetails(RestaurantInfo restaurantInfo) {
        activity.startActivityForResult(RestaurantDetailsActivity.createIntent(activity, restaurantInfo), UPDATE_RESTAURANT_CODE);
    }
}
