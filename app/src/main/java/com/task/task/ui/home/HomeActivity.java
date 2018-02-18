package com.task.task.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.utils.SnackBarHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.task.injection.module.ManagerModule.VERTICAL_LL_MANAGER;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_DOWNLOADED;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_ERROR_DOWNLOADING;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_INFO;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_LOADED_FROM_DB;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_NOT_DOWNLOADED_NO_INTERNET;
import static com.task.task.utils.Constants.HomeActivityConstants.UPDATE_RESTAURANT_CODE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.ADDED;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_ADD;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_EDIT;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_UPDATED_ADDED_EXTRA;


public class HomeActivity extends BaseActivity implements HomeView, HomeActivityRecyclerViewAdapter.Listener {

    @Inject
    HomePresenter presenter;

    @Inject
    StringManager stringManager;

    @Inject
    @Named(VERTICAL_LL_MANAGER)
    LinearLayoutManager linearLayoutManager;

    @Inject
    HomeActivityRecyclerViewAdapter homeActivityRecyclerViewAdapter;

    @Inject
    HomeRouter router;

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
                SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.data_loaded_and_saved_locally),
                        R.color.snackbar_green);
                break;
            case DATA_ERROR_DOWNLOADING:
                SnackBarHelper.setUpSnackBar(this, R.drawable.ic_alert_circle_outline,
                        stringManager.getString(R.string.data_failure_downloading),
                        R.color.snackbar_red);
                break;
            case DATA_LOADED_FROM_DB:
                SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.data_loaded_from_db),
                        R.color.snackbar_green);
                break;
            case DATA_NOT_DOWNLOADED_NO_INTERNET:
                SnackBarHelper.setUpSnackBar(this, R.drawable.ic_alert_circle_outline,
                        stringManager.getString(R.string.data_not_downloaded_no_internet),
                        R.color.snackbar_red);
                break;
            default:
                SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.data_loaded_from_db),
                        R.color.snackbar_green);
                break;
        }

    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.home_activity_toolbar_title));
        }
    }

    @OnClick(R.id.home_activity_fab)
    public void fabClicked() {
        router.onRestaurantDetailsOrAddNew(RestaurantInfo.EMPTY, RESTAURANT_ADD);

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
            presenter.deleteRestaurant(restaurantInfo.id);
        } else {
            router.onRestaurantDetailsOrAddNew(restaurantInfo, RESTAURANT_EDIT);
        }
    }

    @Override
    public void restaurantDeleted() {
        SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                stringManager.getString(R.string.home_activity_restaurant_deleted),
                R.color.snackbar_green);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_RESTAURANT_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra(RESTAURANT_UPDATED_ADDED_EXTRA).equals(ADDED)) {
                    SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                            stringManager.getString(R.string.restaurant_added_successfully),
                            R.color.snackbar_green);
                } else {
                    SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                            stringManager.getString(R.string.restaurant_details_activity_restaurant_updated),
                            R.color.snackbar_green);
                }
                presenter.getRestaurants();
            }
        }
    }
}
