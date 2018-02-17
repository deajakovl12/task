package com.task.task.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    SplashPresenter presenter;
    @Inject
    SplashRouter router;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getRestaurantInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.dispose();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void dataLoaded(String infoAboutData) {
        new Handler().postDelayed(() -> router.goToHomeScreen(infoAboutData),
                getResources().getInteger(R.integer.splash_display_duration));
    }
}