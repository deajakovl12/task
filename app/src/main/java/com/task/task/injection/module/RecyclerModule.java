package com.task.task.injection.module;


import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.task.task.injection.scope.ForActivity;
import com.task.task.ui.gallery.GalleryPhotoRecyclerViewAdapter;
import com.task.task.ui.home.HomeActivityRecyclerViewAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.task.task.injection.module.ManagerModule.HORIZONTAL_LL_MANAGER;
import static com.task.task.injection.module.ManagerModule.VERTICAL_LL_MANAGER;

@Module
public class RecyclerModule {

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

    @ForActivity
    @Provides
    @Named(VERTICAL_LL_MANAGER)
    LinearLayoutManager provideLinearLayoutManager(final Activity activity) {
        return new LinearLayoutManager(activity);
    }

    @ForActivity
    @Provides
    @Named(HORIZONTAL_LL_MANAGER)
    LinearLayoutManager provideLinearLayoutManagerHorizontal(final Activity activity) {
        return new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
    }
}
