package com.task.task.injection.module;

import com.task.task.application.TaskApplication;
import com.task.task.manager.StringManager;
import com.task.task.manager.StringManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ManagerModule {

    @Provides
    @Singleton
    StringManager provideStringManager(final TaskApplication application) {
        return new StringManagerImpl(application.getResources());
    }
}
