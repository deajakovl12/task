package com.task.task.ui.details;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.ui.gallery.GalleryActivity;

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
        showAddProfilePhotoDialog();
    }

    private void showAddProfilePhotoDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.take_or_choose_photo);

        RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = dialog.findViewById(checkedId);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                if (rb.getText().equals(getString(R.string.take_photo))) {
                    takeAnotherPhoto();
                } else {
                    addPhotoFromGallery();
                }
                dialog.dismiss();
            }, 500);
        });
        dialog.show();
    }

    private void addPhotoFromGallery() {
        checkPermission();
    }

    private void checkPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivity(new Intent(RestaurantDetailsActivity.this, GalleryActivity.class));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(RestaurantDetailsActivity.this, "Not graned", Toast.LENGTH_SHORT).show();
                        if (response.isPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void takeAnotherPhoto() {
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                photoFile = new File(MyProfileActivity.this.getExternalCacheDir(), "image_taken.jpeg");

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }*/
        Toast.makeText(this, "NEW", Toast.LENGTH_SHORT).show();
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
