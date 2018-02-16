package com.task.task.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.ui.home.HomeActivity;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    SplashPresenter presenter;


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
    public void showData(List<RestaurantInfo> restaurantInfo) {
        Timber.e(restaurantInfo.get(0).name);
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, HomeActivity.class)),
                getResources().getInteger(R.integer.splash_display_duration));
    }
}