package com.task.task.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_DETAILS_INFO;

public class RestaurantDetailsActivity extends BaseActivity implements RestaurantDetailsView {


    @Inject
    RestaurantDetailsPresenter presenter;

    RestaurantInfo restaurantInfo;

    @BindView(R.id.restaurant_details_activity_image)
    ImageView restaurantImage;

    @BindView(R.id.activity_restaurant_details_camera)
    ImageView camera;

    @BindView(R.id.activity_restaurant_details_restaurant_name)
    TextInputEditText restauantName;

    @BindView(R.id.activity_restaurant_details_restaurant_address)
    TextInputEditText restaurantAddress;

    @BindView(R.id.activity_restaurant_details_longitude)
    TextView restaurantLongitude;

    @BindView(R.id.activity_restaurant_details_latitude)
    TextView restaurantLatitude;


    public static Intent createIntent(final Context context, final RestaurantInfo restaurantInfo) {
        return new Intent(context, RestaurantDetailsActivity.class).putExtra(RESTAURANT_DETAILS_INFO, restaurantInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        ButterKnife.bind(this);
        setUpToolbar();

        restaurantInfo = getIntent().getParcelableExtra(RESTAURANT_DETAILS_INFO);
        showDataToUser();
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_details_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_restaurant_details_save:
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @OnClick(R.id.activity_restaurant_details_camera)
    public void addOrChangeImage() {
        Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.restaurant_details_activity_toolbar_title));
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    private void showDataToUser() {
        restauantName.setText(restaurantInfo.name);
        restauantName.setSelection(restauantName.getText().length());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        restaurantAddress.setText(restaurantInfo.address);
        restaurantLongitude.setText(String.valueOf(restaurantInfo.longitude));
        restaurantLatitude.setText(String.valueOf(restaurantInfo.latitude));
    }
}
