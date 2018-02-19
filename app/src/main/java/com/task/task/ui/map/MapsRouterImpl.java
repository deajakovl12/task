package com.task.task.ui.map;


import android.app.Activity;

import com.task.task.domain.model.RestaurantInfo;
import com.task.task.ui.details.RestaurantDetailsAddNewAddNewActivity;

import static com.task.task.utils.Constants.HomeActivityConstants.UPDATE_RESTAURANT_CODE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_EDIT;

public class MapsRouterImpl implements MapsRouter {
    private final Activity activity;

    public MapsRouterImpl(final Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onRestaurantDetailsOrAddNew(RestaurantInfo restaurantInfo) {
        activity.startActivityForResult(RestaurantDetailsAddNewAddNewActivity.createIntent(activity, restaurantInfo, RESTAURANT_EDIT), UPDATE_RESTAURANT_CODE);

    }
}
