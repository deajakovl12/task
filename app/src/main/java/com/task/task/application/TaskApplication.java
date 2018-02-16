package com.task.task.application;

import android.app.Application;

import com.task.task.injection.ComponentFactory;
import com.task.task.injection.component.ApplicationComponent;

import timber.log.Timber;

public final class TaskApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);
        Timber.plant(new Timber.DebugTree());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
