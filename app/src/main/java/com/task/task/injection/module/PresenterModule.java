package com.task.task.injection.module;

import com.task.task.data.api.converter.RestaurantsAPIConverter;
import com.task.task.data.storage.PreferenceRepository;
import com.task.task.data.storage.TaskPreferences;
import com.task.task.domain.usecase.LocalImagesUseCase;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.injection.scope.ForActivity;
import com.task.task.manager.NetworkManager;
import com.task.task.manager.StringManager;
import com.task.task.ui.details.RestaurantDetailsAddNewPresenter;
import com.task.task.ui.details.RestaurantDetailsAddNewPresenterImpl;
import com.task.task.ui.gallery.GalleryActivityPresenter;
import com.task.task.ui.gallery.GalleryActivityPresenterImpl;
import com.task.task.ui.home.HomePresenter;
import com.task.task.ui.home.HomePresenterImpl;
import com.task.task.ui.map.MapsPresenter;
import com.task.task.ui.map.MapsPresenterImpl;
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
                                       @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, RestaurantUseCase restaurantUseCase, StringManager stringManager) {
        return new HomePresenterImpl(subscribeScheduler, observeScheduler, restaurantUseCase, stringManager);
    }

    @ForActivity
    @Provides
    SplashPresenter provideSplashPresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                           @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler,
                                           RestaurantUseCase restaurantUseCase, RestaurantsAPIConverter restaurantsAPIConverter,
                                           NetworkManager networkManager, StringManager stringManager, PreferenceRepository preferenceRepository) {
        return new SplashPresenterImpl(subscribeScheduler, observeScheduler, restaurantUseCase,
                restaurantsAPIConverter, networkManager, stringManager, preferenceRepository);
    }

    @ForActivity
    @Provides
    RestaurantDetailsAddNewPresenter provideRestaurantDetailsPresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                                                       @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler,
                                                                       RestaurantUseCase restaurantUseCase, StringManager stringManager,
                                                                       PreferenceRepository preferenceRepository) {
        return new RestaurantDetailsAddNewPresenterImpl(subscribeScheduler, observeScheduler, restaurantUseCase, stringManager, preferenceRepository);
    }


    @ForActivity
    @Provides
    GalleryActivityPresenter provideGalleryActivityPresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                                             @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, LocalImagesUseCase localImagesUseCase,
                                                             StringManager stringManager) {
        return new GalleryActivityPresenterImpl(subscribeScheduler, observeScheduler, stringManager, localImagesUseCase);
    }


    @ForActivity
    @Provides
    MapsPresenter provideMapsPresenter(@Named(SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                       @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, RestaurantUseCase restaurantUseCase, StringManager stringManager) {
        return new MapsPresenterImpl(subscribeScheduler, observeScheduler, restaurantUseCase, stringManager);
    }

}
