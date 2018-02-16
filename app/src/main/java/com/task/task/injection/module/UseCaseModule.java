package com.task.task.injection.module;

import com.task.task.data.service.NetworkService;
import com.task.task.data.storage.TemplatePreferences;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.domain.usecase.RestaurantUseCaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UseCaseModule {


    @Provides
    @Singleton
    RestaurantUseCase providePersonUseCase(final TemplatePreferences preferences, final NetworkService networkService) {
        return new RestaurantUseCaseImpl(networkService, preferences);
    }

}
