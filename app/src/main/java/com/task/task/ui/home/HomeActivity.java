package com.task.task.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.activities.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.task.task.utils.Constants.HomeActivityConstants.DATA_DOWNLOADED;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_ERROR_DOWNLOADING;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_INFO;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_LOADED_FROM_DB;
import static com.task.task.utils.Constants.HomeActivityConstants.DATA_NOT_DOWNLOADED_NO_INTERNET;
import static com.task.task.utils.Constants.SnackBarConstants.ICON_PADDING;
import static com.task.task.utils.Constants.SnackBarConstants.ICON_SIZE;


public class HomeActivity extends BaseActivity implements HomeView {

    @Inject
    HomePresenter presenter;

    @Inject
    StringManager stringManager;

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

    private void setUpSnackBar(int iconImage, String snackText, int backgroundColor){
        TSnackbar snackbar = TSnackbar
                .make(findViewById(android.R.id.content), snackText, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setIconRight(iconImage, ICON_SIZE);
        snackbar.setIconPadding(ICON_PADDING);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        snackbar.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getMovieInfo();
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
        Toast.makeText(this, restaurantInfo.get(1).name + restaurantInfo.get(1).id, Toast.LENGTH_SHORT).show();
    }
}
