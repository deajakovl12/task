package com.task.task.injection.module;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.task.task.application.TaskApplication;
import com.task.task.injection.scope.ForActivity;
import com.task.task.ui.details.RestaurantDetailsRouter;
import com.task.task.ui.details.RestaurantDetailsRouterImpl;
import com.task.task.ui.home.HomeRouter;
import com.task.task.ui.home.HomeRouterImpl;
import com.task.task.ui.splash.SplashRouter;
import com.task.task.ui.splash.SplashRouterImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.task.task.injection.module.ManagerModule.HORIZONTAL_LL_MANAGER;
import static com.task.task.injection.module.ManagerModule.VERTICAL_LL_MANAGER;

@Module
public final class RouterModule {

    @ForActivity
    @Provides
    HomeRouter provideHomeRouter(final Activity activity) {
        return new HomeRouterImpl(activity);
    }

    @ForActivity
    @Provides
    SplashRouter provideSplashRouter(final Activity activity) {
        return new SplashRouterImpl(activity);
    }

    @ForActivity
    @Provides
    RestaurantDetailsRouter provideRestaurantDetailsRouter(final Activity activity) {
        return new RestaurantDetailsRouterImpl(activity);
    }

}
