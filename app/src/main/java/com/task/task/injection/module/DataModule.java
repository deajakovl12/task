package com.task.task.injection.module;

import android.provider.Settings;

import com.google.android.gms.tasks.Task;
import com.task.task.application.TaskApplication;
import com.task.task.data.storage.PreferenceRepository;
import com.task.task.data.storage.SecureSharedPreferences;
import com.task.task.data.storage.TaskPreferences;
import com.task.task.data.storage.database.DatabaseHelper;
import com.task.task.data.storage.database.DatabaseHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DataModule {

    private static final String PREFS_NAME = "taskSecureStorage";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "task.db";


    @Singleton
    @Provides
    TaskPreferences provideTaskPreferences(final SecureSharedPreferences securePreferences) {
        return TaskPreferences.create(securePreferences);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(final TaskPreferences taskPreferences) {
        return taskPreferences;
    }

    @Provides
    @Singleton
    public SecureSharedPreferences provideSecureSharedPreferences(final TaskApplication context) {
        final String androidSecret = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return new SecureSharedPreferences(context, context.getSharedPreferences(PREFS_NAME, 0), androidSecret);
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(final TaskApplication application, final PreferenceRepository preferenceRepository) {
        return new DatabaseHelperImpl(application, DATABASE_NAME, DATABASE_VERSION, preferenceRepository);
    }
}
