package com.task.task.injection.component;


import com.task.task.injection.module.ActivityModule;
import com.task.task.injection.module.AdapterModule;
import com.task.task.injection.module.PresenterModule;
import com.task.task.injection.module.RouterModule;
import com.task.task.injection.scope.ForActivity;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.ui.gallery.GalleryActivityPresenter;
import com.task.task.ui.home.HomeActivityRecyclerViewAdapter;
import com.task.task.ui.home.HomePresenter;
import com.task.task.ui.home.HomeRouter;
import com.task.task.ui.splash.SplashPresenter;
import com.task.task.ui.splash.SplashRouter;

import dagger.Component;


@ForActivity
@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                ActivityModule.class,
                PresenterModule.class,
                RouterModule.class,
                AdapterModule.class
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

}

