package com.task.task.ui.details;


import android.app.Activity;

import com.task.task.ui.gallery.GalleryActivity;

import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.PICK_GALLERY_IMAGE_CODE;

public class RestaurantDetailsAddNewRouterImpl implements RestaurantDetailsAddNewRouter {

    private final Activity activity;

    public RestaurantDetailsAddNewRouterImpl(final Activity activity) {
        this.activity = activity;
    }


    @Override
    public void openGallery() {
        activity.startActivityForResult(GalleryActivity.createIntent(activity), PICK_GALLERY_IMAGE_CODE);
    }

    @Override
    public void openCamera() {
        //activity.startActivity(RestaurantDetailsAddNewAddNewActivity.createIntent(activity, restaurantInfo));
    }
}
