package com.task.task.ui.map;


import com.task.task.R;
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

public class MapsPresenterImpl extends BasePresenter implements MapsPresenter {

    private MapsView view;

    private final RestaurantUseCase restaurantUseCase;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final StringManager stringManager;

    public MapsPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                             @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final RestaurantUseCase restaurantUseCase,
                             final StringManager stringManager) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.restaurantUseCase = restaurantUseCase;
        this.stringManager = stringManager;
    }

    @Override
    public void setView(final MapsView view) {
        this.view = view;
    }

    @Override
    public void getRestaurants() {
        if (view != null) {
            addDisposable(restaurantUseCase.getLocalRestaurantData()
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observeScheduler)
                    .subscribe(this::onGetRestaurantsSuccess, this::onGetRestauntsFailure));
        }
    }

    private void onGetRestauntsFailure(final Throwable throwable) {
        Timber.e(stringManager.getString(R.string.fetch_restaurant_data), throwable);
    }

    private void onGetRestaurantsSuccess(final List<RestaurantInfo> restaurantInfo) {
        if (view != null) {
            view.showData(restaurantInfo);
        }
    }
}
