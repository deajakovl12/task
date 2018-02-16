package com.task.task.ui.splash;


import com.task.task.R;
import com.task.task.data.api.converter.RestaurantsAPIConverter;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.presenter.BasePresenter;
import com.task.task.ui.home.HomeView;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Scheduler;
import timber.log.Timber;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;

public class SplashPresenterImpl extends BasePresenter implements SplashPresenter {

    private SplashView view;

    private final RestaurantUseCase restaurantUseCase;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final RestaurantsAPIConverter restaurantsAPIConverter;

    private final StringManager stringManager;

    public SplashPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                               @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final RestaurantUseCase restaurantUseCase,
                               final RestaurantsAPIConverter restaurantsAPIConverter, final StringManager stringManager) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.restaurantUseCase = restaurantUseCase;
        this.restaurantsAPIConverter = restaurantsAPIConverter;
        this.stringManager = stringManager;
    }

    @Override
    public void setView(final SplashView view) {
        this.view = view;
    }

    @Override
    public void getRestaurantInfo() {
        if (view != null) {
            addDisposable(restaurantUseCase.getRestaurantInfo()
                    .map(restaurantsAPIConverter::convertToRestarauntInfo)
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observeScheduler)
                    .subscribe(this::onGetMovieInfoSuccess, this::onGetMovieInfoFailure));
        }
    }

    private void onGetMovieInfoFailure(final Throwable throwable) {
        Timber.e(stringManager.getString(R.string.fetch_movie_info_error), throwable);
    }

    private void onGetMovieInfoSuccess(final List<RestaurantInfo> restaurantInfo) {
        if (view != null) {
            view.showData(restaurantInfo);
        }
    }
}
