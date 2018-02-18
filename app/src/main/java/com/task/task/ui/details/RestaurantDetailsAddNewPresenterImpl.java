package com.task.task.ui.details;


import com.task.task.data.storage.PreferenceRepository;
import com.task.task.data.storage.TaskPreferences;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.presenter.BasePresenter;

import javax.inject.Named;

import io.reactivex.Scheduler;
import timber.log.Timber;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;

public class RestaurantDetailsAddNewPresenterImpl extends BasePresenter implements RestaurantDetailsAddNewPresenter {


    private RestaurantDetailsAddNewView view;

    private final RestaurantUseCase restaurantUseCase;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final StringManager stringManager;

    private final PreferenceRepository preferenceRepository;

    public RestaurantDetailsAddNewPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                                                @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler, final RestaurantUseCase restaurantUseCase,
                                                final StringManager stringManager, final PreferenceRepository preferenceRepository) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.restaurantUseCase = restaurantUseCase;
        this.stringManager = stringManager;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void setView(RestaurantDetailsAddNewView view) {
        this.view = view;
    }

    @Override
    public void updateRestaurantData(RestaurantInfo restaurantInfo) {
        addDisposable(restaurantUseCase.updateRestaurantData(restaurantInfo)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(this::onUpdateRestaurantDataSuccess, this::onUpdateRestaurantDataFailure));
    }


    private void onUpdateRestaurantDataFailure(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    private void onUpdateRestaurantDataSuccess() {
        if (view != null) {
            view.restaurantDataUpdated();
        }
    }

    @Override
    public void addNewRestaurant(RestaurantInfo restaurantInfo) {
        addDisposable(restaurantUseCase.addNewRestaurant(restaurantInfo)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(this::onAddNewRestaurantSuccess, this::onAddNewRestaurantFailure));
    }

    private void onAddNewRestaurantFailure(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    private void onAddNewRestaurantSuccess() {
        if(view!=null){
            view.restaurantAdded();
        }

    }

    @Override
    public int getLastRestaurantId() {
        return preferenceRepository.getLastRestaurantId();
    }
}
