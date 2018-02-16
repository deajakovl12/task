package com.task.task.injection.module;

import com.task.task.data.api.converter.RestaurantsAPIConverter;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.injection.scope.ForActivity;
import com.task.task.manager.StringManager;
import com.task.task.ui.home.HomePresenter;
import com.task.task.ui.home.HomePresenterImpl;
import com.task.task.ui.splash.SplashPresenter;
import com.task.task.ui.splash.SplashPresenterImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;


@Module
public final class PresenterModule {

    @ForActivity
    @Provides
    HomePresenter provideHomePresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                       @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, RestaurantUseCase restaurantUseCase, RestaurantsAPIConverter restaurantsAPIConverter, StringManager stringManager) {
        return new HomePresenterImpl(subscribeScheduler, observeScheduler, restaurantUseCase, restaurantsAPIConverter, stringManager);
    }

    @ForActivity
    @Provides
    SplashPresenter provideSplashPresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                         @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, RestaurantUseCase restaurantUseCase, RestaurantsAPIConverter restaurantsAPIConverter, StringManager stringManager) {
        return new SplashPresenterImpl(subscribeScheduler, observeScheduler, restaurantUseCase, restaurantsAPIConverter, stringManager);
    }
}
