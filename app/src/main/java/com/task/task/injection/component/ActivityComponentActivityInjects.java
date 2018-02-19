package com.task.task.injection.component;


import com.task.task.ui.details.RestaurantDetailsAddNewAddNewActivity;
import com.task.task.ui.gallery.GalleryActivity;
import com.task.task.ui.home.HomeActivity;
import com.task.task.ui.map.MapsActivity;
import com.task.task.ui.splash.SplashActivity;

public interface ActivityComponentActivityInjects {

    void inject(HomeActivity homeActivity);

    void inject(SplashActivity splashActivity);

    void inject(RestaurantDetailsAddNewAddNewActivity restaurantDetailsAddNewActivity);

    void inject(GalleryActivity galleryActivity);

    void inject(MapsActivity mapsActivity);


}
