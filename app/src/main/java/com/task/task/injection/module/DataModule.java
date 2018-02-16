package com.task.task.injection.module;

import android.provider.Settings;

import com.task.task.application.TaskApplication;
import com.task.task.data.storage.PreferenceRepository;
import com.task.task.data.storage.SecureSharedPreferences;
import com.task.task.data.storage.TemplatePreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DataModule {

    private static final String PREFS_NAME = "infinumSecureStorage";

    @Singleton
    @Provides
    TemplatePreferences provideTemplatePreferences(final SecureSharedPreferences securePreferences) {
        return TemplatePreferences.create(securePreferences);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(final TemplatePreferences templatePreferences) {
        return templatePreferences;
    }

    @Provides
    @Singleton
    public SecureSharedPreferences provideSecureSharedPreferences(final TaskApplication context) {
        final String androidSecret = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return new SecureSharedPreferences(context, context.getSharedPreferences(PREFS_NAME, 0), androidSecret);
    }
}
