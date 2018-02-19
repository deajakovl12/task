package com.task.task.injection.component;


import android.support.v7.widget.LinearLayoutManager;

import com.task.task.injection.module.ActivityModule;
import com.task.task.injection.module.PresenterModule;
import com.task.task.injection.module.RecyclerModule;
import com.task.task.injection.module.RouterModule;
import com.task.task.injection.scope.ForActivity;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.ui.details.RestaurantDetailsAddNewRouter;
import com.task.task.ui.gallery.GalleryActivityPresenter;
import com.task.task.ui.gallery.GalleryPhotoRecyclerViewAdapter;
import com.task.task.ui.home.HomeActivityRecyclerViewAdapter;
import com.task.task.ui.home.HomePresenter;
import com.task.task.ui.home.HomeRouter;
import com.task.task.ui.map.MapsPresenter;
import com.task.task.ui.map.MapsRouter;
import com.task.task.ui.splash.SplashPresenter;
import com.task.task.ui.splash.SplashRouter;

import javax.inject.Named;

import dagger.Component;

import static com.task.task.injection.module.ManagerModule.HORIZONTAL_LL_MANAGER;
import static com.task.task.injection.module.ManagerModule.VERTICAL_LL_MANAGER;


@ForActivity
@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                ActivityModule.class,
                PresenterModule.class,
                RouterModule.class,
                RecyclerModule.class
        }
)
public interface ActivityComponent extends ActivityComponentActivityInjects, ActivityComponentFragmentsInjects {

    final class Initializer {

        private Initializer() {
        }

        public static ActivityComponent init(final ApplicationComponent applicationComponent, final BaseActivity activity) {
            return DaggerActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .activityModule(new ActivityModule(activity))
                    .build();
        }
    }

    HomeRouter getHomeRouter();

    HomePresenter getHomePresenter();

    SplashRouter getSplashRouter();

    SplashPresenter getSplashPresenter();

    HomeActivityRecyclerViewAdapter provideHomeActivityRecyclerViewAdapter();

    GalleryActivityPresenter getGalleryActivityPresenter();

    RestaurantDetailsAddNewRouter getRestaurantDetailsRouter();

    GalleryPhotoRecyclerViewAdapter getGalleryPhotoRecyclerViewAdapter();

    @Named(VERTICAL_LL_MANAGER)
    LinearLayoutManager getLinearLayoutManager();

    @Named(HORIZONTAL_LL_MANAGER)
    LinearLayoutManager getLinearLayoutManagerHorizontal();

    MapsPresenter getMapsPresenter();

    MapsRouter getMapsRouter();
}

