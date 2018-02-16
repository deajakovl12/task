package com.task.task.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class HomeActivity extends BaseActivity implements HomeView {

    @Inject
    HomePresenter presenter;

    public static Intent createIntent(final Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //presenter.setView(this);
        //presenter.getMovieInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //presenter.dispose();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showData(final List<RestaurantInfo> restaurantInfo) {
        Toast.makeText(this, restaurantInfo.get(1).name + restaurantInfo.get(1).id, Toast.LENGTH_SHORT).show();
    }
}
