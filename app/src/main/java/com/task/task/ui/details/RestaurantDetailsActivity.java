package com.task.task.ui.details;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.PICK_GALLERY_IMAGE_CODE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.PICK_GALLERY_IMAGE_EXTRA;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.REQUEST_IMAGE_CAPTURE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_DETAILS_INFO;

public class RestaurantDetailsActivity extends BaseActivity implements RestaurantDetailsView {


    @Inject
    RestaurantDetailsPresenter presenter;

    @Inject
    RestaurantDetailsRouter router;

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

    private Uri imageUri;
    private File photoFile = null;

    public static Intent createIntent(final Context context, final RestaurantInfo restaurantInfo) {
        return new Intent(context, RestaurantDetailsActivity.class).putExtra(RESTAURANT_DETAILS_INFO, restaurantInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        ButterKnife.bind(this);
        checkPermissionExternalStorage();

    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
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
                restaurantInfo.name = restauantName.getText().toString();
                restaurantInfo.address = restaurantAddress.getText().toString();
                restaurantInfo.imageUri = imageUri;
                presenter.updateRestaurantData(restaurantInfo);
                break;
            case android.R.id.home:
                onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_GALLERY_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                imageUri = Uri.parse(data.getStringExtra(PICK_GALLERY_IMAGE_EXTRA));
                Glide.with(this).load(data.getStringExtra(PICK_GALLERY_IMAGE_EXTRA)).centerCrop().into(restaurantImage);
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri photoURI = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
            } else {
                if (photoFile != null) {
                    photoURI = Uri.fromFile(photoFile);
                }
            }
            imageUri = photoURI;
            Glide.with(this).load(String.valueOf(imageUri)).centerCrop().into(restaurantImage);

        }
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
                    takePhoto();
                } else {
                    addPhotoFromGallery();
                }
                dialog.dismiss();
            }, 500);
        });
        dialog.show();
    }

    private void addPhotoFromGallery() {
        router.openGallery();
    }

    private void checkPermissionExternalStorage() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setUpToolbar();

                        restaurantInfo = getIntent().getParcelableExtra(RESTAURANT_DETAILS_INFO);
                        imageUri = restaurantInfo.imageUri;
                        showDataToUser();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Timber.e(getString(R.string.permission_not_granted));
                        finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = new File(RestaurantDetailsActivity.this.getExternalCacheDir(), restaurantInfo.id + ".jpeg");

            } catch (Exception e) {
                Timber.e(e.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
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
        Glide.with(this).load(String.valueOf(restaurantInfo.imageUri)).centerCrop().into(restaurantImage);
    }

    @Override
    public void restaurantDataUpdated() {
        setResult(Activity.RESULT_OK);
        finish();
    }

}
