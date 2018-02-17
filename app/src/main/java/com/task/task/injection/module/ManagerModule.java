package com.task.task.injection.module;

import android.net.ConnectivityManager;
import android.support.v7.widget.LinearLayoutManager;

import com.task.task.application.TaskApplication;
import com.task.task.manager.NetworkManager;
import com.task.task.manager.NetworkManagerImpl;
import com.task.task.manager.StringManager;
import com.task.task.manager.StringManagerImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.CONNECTIVITY_SERVICE;

@Module
public final class ManagerModule {

    public static final String VERTICAL_LL_MANAGER = "vertical_ll_manager";

    public static final String HORIZONTAL_LL_MANAGER = "horizontal_ll_manager";

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
}
