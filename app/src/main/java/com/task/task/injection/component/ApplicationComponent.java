package com.task.task.injection.component;

import com.task.task.application.TaskApplication;
import com.task.task.data.api.converter.MovieAPIConverter;
import com.task.task.data.service.NetworkService;
import com.task.task.data.storage.TemplatePreferences;
import com.task.task.device.ApplicationInformation;
import com.task.task.device.DeviceInformation;
import com.task.task.domain.usecase.MovieUseCase;
import com.task.task.injection.module.ApiModule;
import com.task.task.injection.module.ApplicationModule;
import com.task.task.injection.module.DataModule;
import com.task.task.injection.module.DeviceModule;
import com.task.task.injection.module.ManagerModule;
import com.task.task.injection.module.ThreadingModule;
import com.task.task.injection.module.UseCaseModule;
import com.task.task.manager.StringManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;


@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                ApiModule.class,
                ManagerModule.class,
                DataModule.class,
                ThreadingModule.class,
                UseCaseModule.class,
                DeviceModule.class
        }
)

public interface ApplicationComponent extends ApplicationComponentInjects {

    final class Initializer {

        private Initializer() {
        }

        public static ApplicationComponent init(final TaskApplication taskApplication) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(taskApplication))
                    .apiModule(new ApiModule())
                    .build();
        }
    }

    @Named(OBSERVE_SCHEDULER)
    Scheduler getObserveScheduler();

    @Named(SUBSCRIBE_SCHEDULER)
    Scheduler getSubscribeScheduler();

    StringManager getStringManager();

    MovieUseCase getMovieUseCase();

    OkHttpClient getOkHttpClient();

    DeviceInformation getDeviceInformation();

    ApplicationInformation getApplicationInformation();

    MovieAPIConverter getMovieApiConverter();

    TemplatePreferences getTemplatePreferences();

    NetworkService getNetworkService();
}
