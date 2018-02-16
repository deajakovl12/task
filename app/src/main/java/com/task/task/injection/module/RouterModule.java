package com.task.task.injection.module;

import android.app.Activity;

import com.task.task.injection.scope.ForActivity;
import com.task.task.ui.home.HomeRouter;
import com.task.task.ui.home.HomeRouterImpl;
import com.task.task.ui.splash.SplashRouter;
import com.task.task.ui.splash.SplashRouterImpl;

import dagger.Module;
import dagger.Provides;

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
        return new SplashRouterImpl(activity) {
        };
    }

}
