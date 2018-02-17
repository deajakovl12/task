package com.task.task.injection.component;


import com.task.task.ui.details.RestaurantDetailsActivity;
import com.task.task.ui.gallery.GalleryActivity;
import com.task.task.ui.home.HomeActivity;
import com.task.task.ui.splash.SplashActivity;

public interface ActivityComponentActivityInjects {

    void inject(HomeActivity homeActivity);

    void inject(SplashActivity splashActivity);

    void inject(RestaurantDetailsActivity restaurantDetailsActivity);

    void inject(GalleryActivity galleryActivity);

}
