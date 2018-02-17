package com.task.task.injection.module;

import com.task.task.application.TaskApplication;
import com.task.task.injection.scope.ForActivity;
import com.task.task.ui.gallery.GalleryPhotoRecyclerViewAdapter;
import com.task.task.ui.home.HomeActivityRecyclerViewAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

    @ForActivity
    @Provides
    HomeActivityRecyclerViewAdapter provideHomeActivityRecyclerViewAdapter() {
        return new HomeActivityRecyclerViewAdapter();
    }

    @ForActivity
    @Provides
    GalleryPhotoRecyclerViewAdapter provideGalleryPhotoRecyclerViewAdapter() {
        return new GalleryPhotoRecyclerViewAdapter();
    }
}
