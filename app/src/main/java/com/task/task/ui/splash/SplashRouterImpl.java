package com.task.task.ui.splash;


import android.app.Activity;

import com.task.task.ui.home.HomeActivity;

public class SplashRouterImpl implements SplashRouter {


    private final Activity activity;


    public SplashRouterImpl(final Activity activity) {
        this.activity = activity;
    }


    @Override
    public void goToHomeScreen(String infoAboutData) {
        activity.startActivity(HomeActivity.createIntent(activity, infoAboutData));
    }
}
