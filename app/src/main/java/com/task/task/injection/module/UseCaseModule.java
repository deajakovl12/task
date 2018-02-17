package com.task.task.injection.module;

import com.task.task.data.service.NetworkService;
import com.task.task.data.storage.TaskPreferences;
import com.task.task.data.storage.database.DatabaseHelper;
import com.task.task.domain.usecase.LocalImagesUseCase;
import com.task.task.domain.usecase.LocalImagesUseCaseImpl;
import com.task.task.domain.usecase.RestaurantUseCase;
import com.task.task.domain.usecase.RestaurantUseCaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UseCaseModule {


    @Provides
    @Singleton
    RestaurantUseCase providePersonUseCase(final TaskPreferences preferences, final NetworkService networkService, final DatabaseHelper databaseHelper) {
        return new RestaurantUseCaseImpl(networkService, preferences, databaseHelper);
    }

    @Provides
    @Singleton
    LocalImagesUseCase provideLocalImagesUseCase() {
        return new LocalImagesUseCaseImpl();
    }

}
