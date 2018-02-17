package com.task.task.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.activities.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.task.utils.Constants.HomeActivityConstants.DATA_DOWNLOADED;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_ERROR_DOWNLOADING;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_INFO;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_LOADED_FROM_DB;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_NOT_DOWNLOADED_NO_INTERNET;
import static com.task.task.utils.Constants.SnackBarConstants.ICON_PADDING;
import static com.task.task.utils.Constants.SnackBarConstants.ICON_SIZE;


public class HomeActivity extends BaseActivity implements HomeView, HomeActivityRecyclerViewAdapter.Listener {

    @Inject
    HomePresenter presenter;

    @Inject
    StringManager stringManager;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    HomeActivityRecyclerViewAdapter homeActivityRecyclerViewAdapter;

    @BindView(R.id.home_activity_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.home_activity_fab)
    FloatingActionButton fab;

    private String infoAboutDownloading;

    public static Intent createIntent(final Context context, final String infoAboutDownloading) {
        return new Intent(context, HomeActivity.class).putExtra(DATA_INFO, infoAboutDownloading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        infoAboutDownloading = getIntent().getStringExtra(DATA_INFO);
        showUserInfoAboutData();
        setUpToolbar();
        setUpRecyclerView();
        setUpFab();

    }

    private void setUpFab() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else if (dy < 0) {
                    fab.show();
                }
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setMotionEventSplittingEnabled(false);
        homeActivityRecyclerViewAdapter.setListener(this);
        recyclerView.setAdapter(homeActivityRecyclerViewAdapter);
    }

    private void showUserInfoAboutData() {
        switch (infoAboutDownloading) {
            case DATA_DOWNLOADED:
                setUpSnackBar(R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.data_loaded_and_saved_locally),
                        R.color.snackbar_green);
                break;
            case DATA_ERROR_DOWNLOADING:
                setUpSnackBar(R.drawable.ic_alert_circle_outline,
                        stringManager.getString(R.string.data_failure_downloading),
                        R.color.snackbar_red);
                break;
            case DATA_LOADED_FROM_DB:
                setUpSnackBar(R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.data_loaded_from_db),
                        R.color.snackbar_green);
                break;
            case DATA_NOT_DOWNLOADED_NO_INTERNET:
                setUpSnackBar(R.drawable.ic_alert_circle_outline,
                        stringManager.getString(R.string.data_not_downloaded_no_internet),
                        R.color.snackbar_red);
                break;
            default:
                setUpSnackBar(R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.data_loaded_from_db),
                        R.color.snackbar_green);
                break;
        }

    }

    private void setUpSnackBar(int iconImage, String snackText, int backgroundColor) {
        TSnackbar snackbar = TSnackbar
                .make(findViewById(android.R.id.content), snackText, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setIconRight(iconImage, ICON_SIZE);
        snackbar.setIconPadding(ICON_PADDING);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        snackbar.show();
    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.home_activity_toolbar_title));
        }
    }

    @OnClick(R.id.home_activity_fab)
    public void fabClicked(){
        Toast.makeText(this, "FAB", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_activity_google_map:
                Toast.makeText(this, "map", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getRestaurants();
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
    public void showData(final List<RestaurantInfo> restaurantInfo) {
        homeActivityRecyclerViewAdapter.setData(restaurantInfo);

    }

    @Override
    public void onRestaurantClicked(RestaurantInfo restaurantInfo, int position, boolean deleteRestaurant) {
        if (deleteRestaurant) {
            Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, restaurantInfo.name + " " + restaurantInfo.id, Toast.LENGTH_SHORT).show();
        }
    }
}
