package com.task.task.ui.splash;


import com.task.task.data.api.converter.RestaurantsAPIConverter;
import com.task.task.data.storage.PreferenceRepository;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.manager.NetworkManager;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.presenter.BasePresenter;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Scheduler;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_DOWNLOADED;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_ERROR_DOWNLOADING;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_LOADED_FROM_DB;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_NOT_DOWNLOADED_NO_INTERNET;

public class SplashPresenterImpl extends BasePresenter implements SplashPresenter {

    private SplashView view;

    private final RestaurantUseCase restaurantUseCase;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final RestaurantsAPIConverter restaurantsAPIConverter;

    private final NetworkManager networkManager;

    private final StringManager stringManager;

    private final PreferenceRepository preferenceRepository;

    public SplashPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                               @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final RestaurantUseCase restaurantUseCase,
                               final RestaurantsAPIConverter restaurantsAPIConverter,
                               final NetworkManager networkManager, final StringManager stringManager,
                               final PreferenceRepository preferenceRepository) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.restaurantUseCase = restaurantUseCase;
        this.restaurantsAPIConverter = restaurantsAPIConverter;
        this.networkManager = networkManager;
        this.stringManager = stringManager;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void setView(final SplashView view) {
        this.view = view;
    }

    @Override
    public void getRestaurantInfo() {
        if (view != null) {
            if (preferenceRepository.getDataDownloaded()) {
                view.dataLoaded(DATA_LOADED_FROM_DB);
            } else {
                if (networkManager.isConnected() || networkManager.isConnectedWifi()) {
                    addDisposable(restaurantUseCase.getRestaurantInfo()
                            .map(restaurantsAPIConverter::convertToRestarauntInfo)
                            .subscribeOn(subscribeScheduler)
                            .observeOn(observeScheduler)
                            .subscribe(this::ongetRestaurantsSuccess, this::onGetRestaurantsFailure));
                } else {
                    noInternetConnection();
                }
            }
        }
    }

    private void onGetRestaurantsFailure(final Throwable throwable) {
        if (view != null) {
            view.dataLoaded(DATA_ERROR_DOWNLOADING);
        }
    }

    private void ongetRestaurantsSuccess(final List<RestaurantInfo> restaurantInfo) {
        addDisposable(restaurantUseCase.addAllRestaurants(restaurantInfo)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(this::onSaveDataLocallySuccess, this::onSaveDataLocallyFailure));

    }

    private void onSaveDataLocallySuccess(Boolean savedOrNot) {
        preferenceRepository.setDataDownoladed();
        if (view != null) {
            view.dataLoaded(DATA_DOWNLOADED);
        }
    }

    private void onSaveDataLocallyFailure(Throwable throwable) {
        if (view != null) {
            view.dataLoaded(DATA_ERROR_DOWNLOADING);
        }

    }

    private void noInternetConnection() {
        if (view != null) {
            view.dataLoaded(DATA_NOT_DOWNLOADED_NO_INTERNET);
        }
    }
}
