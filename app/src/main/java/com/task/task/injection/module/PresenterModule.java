package com.task.task.injection.module;

import com.task.task.data.api.converter.MovieAPIConverter;
import com.task.task.domain.usecase.MovieUseCase;
import com.task.task.injection.scope.ForActivity;
import com.task.task.manager.StringManager;
import com.task.task.ui.home.HomePresenter;
import com.task.task.ui.home.HomePresenterImpl;

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
                                       @Named(OBSERVE_SCHEDULER) Scheduler observeScheduler, MovieUseCase movieUseCase, MovieAPIConverter movieAPIConverter, StringManager stringManager) {
        return new HomePresenterImpl(subscribeScheduler, observeScheduler, movieUseCase, movieAPIConverter, stringManager);
    }

}
