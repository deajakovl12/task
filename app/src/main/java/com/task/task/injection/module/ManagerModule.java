package com.task.task.injection.module;

import android.net.ConnectivityManager;
import android.support.v7.widget.LinearLayoutManager;

import com.task.task.application.TaskApplication;
import com.task.task.manager.NetworkManager;
import com.task.task.manager.NetworkManagerImpl;
import com.task.task.manager.StringManager;
import com.task.task.manager.StringManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.CONNECTIVITY_SERVICE;

@Module
public final class ManagerModule {

    @Provides
    @Singleton
    StringManager provideStringManager(final TaskApplication application) {
        return new StringManagerImpl(application.getResources());
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager(final TaskApplication application) {
        return (ConnectivityManager) application.getSystemService(CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    NetworkManager provideNetworkManager(final ConnectivityManager connectivityManager) {
        return new NetworkManagerImpl(connectivityManager);
    }

    @Provides
    @Singleton
    LinearLayoutManager provideLinearLayoutManager(final TaskApplication application) {
        return new LinearLayoutManager(application);
    }
}
