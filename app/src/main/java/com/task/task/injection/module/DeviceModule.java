package com.task.task.injection.module;

import com.task.task.application.TaskApplication;
import com.task.task.device.ApplicationInformation;
import com.task.task.device.ApplicationInformationImpl;
import com.task.task.device.DeviceInformation;
import com.task.task.device.DeviceInformationImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DeviceModule {

    @Provides
    @Singleton
    public DeviceInformation provideDeviceInformation() {
        return new DeviceInformationImpl();
    }

    @Provides
    @Singleton
    public ApplicationInformation provideApplicationInformation(final TaskApplication application) {
        return new ApplicationInformationImpl(application, application.getPackageManager());
    }
}
