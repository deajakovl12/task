package com.task.task.ui.details;


import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.presenter.BasePresenter;

import javax.inject.Named;

import io.reactivex.Scheduler;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;

public class RestaurantDetailsPresenterImpl extends BasePresenter implements RestaurantDetailsPresenter{


    private RestaurantDetailsView view;

    private final RestaurantUseCase restaurantUseCase;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final StringManager stringManager;

    public RestaurantDetailsPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                             @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final RestaurantUseCase restaurantUseCase,
                             final StringManager stringManager) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.restaurantUseCase = restaurantUseCase;
        this.stringManager = stringManager;
    }

    @Override
    public void setView(RestaurantDetailsView view) {
        this.view = view;
    }

    @Override
    public void getRestaurants() {

    }
}