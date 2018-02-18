package com.task.task.ui.details;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.utils.LocationHelper;
import com.task.task.utils.SnackBarHelper;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.ADDED;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.PICK_GALLERY_IMAGE_CODE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.PICK_GALLERY_IMAGE_EXTRA;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.REQUEST_IMAGE_CAPTURE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_ADD_OR_EDIT;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_DETAILS_INFO;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_EDIT;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.RESTAURANT_UPDATED_ADDED_EXTRA;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.UPDATED;

public class RestaurantDetailsAddNewAddNewActivity extends BaseActivity implements RestaurantDetailsAddNewView,
        OnSuccessListener<Void>, OnFailureListener {


    @Inject
    RestaurantDetailsAddNewPresenter presenter;

    @Inject
    RestaurantDetailsAddNewRouter router;

    @Inject
    StringManager stringManager;

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
    private String addOrEdit;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    public static Intent createIntent(final Context context, final RestaurantInfo restaurantInfo, final String addOrEdit) {
        return new Intent(context, RestaurantDetailsAddNewAddNewActivity.class).putExtra(RESTAURANT_DETAILS_INFO, restaurantInfo).putExtra(RESTAURANT_ADD_OR_EDIT, addOrEdit);
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
                if (addOrEdit.equals(RESTAURANT_EDIT)) {
                    presenter.updateRestaurantData(restaurantInfo);
                } else {
                    if (restaurantInfo.name.isEmpty() ||
                            restaurantInfo.address.isEmpty() ||
                            restaurantLatitude.getText().toString().isEmpty() ||
                            restaurantLongitude.getText().toString().isEmpty()) {
                        SnackBarHelper.setUpSnackBar(this, R.drawable.ic_alert_circle_outline,
                                stringManager.getString(R.string.enter_all_information_about_restaurant),
                                R.color.snackbar_red);
                    } else {
                        restaurantInfo.latitude = Float.parseFloat(restaurantLatitude.getText().toString());
                        restaurantInfo.longitude = Float.parseFloat(restaurantLongitude.getText().toString());
                        presenter.addNewRestaurant(restaurantInfo);
                    }
                }
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
                setUpImage(imageUri);
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
            setUpImage(imageUri);
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
                        restaurantInfo = getIntent().getParcelableExtra(RESTAURANT_DETAILS_INFO);
                        addOrEdit = getIntent().getStringExtra(RESTAURANT_ADD_OR_EDIT);

                        restauantName.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        restaurantAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        restauantName.setSingleLine();
                        restaurantAddress.setSingleLine();

                        setUpToolbar();
                        if (addOrEdit.equals(RESTAURANT_EDIT)) {
                            imageUri = restaurantInfo.imageUri;
                            showDataToUser();
                        } else {
                            checkLocationPermission();
                        }
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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

    private void checkLocationPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            restaurantInfo.id = presenter.getLastRestaurantId() + 1;

                            mLocationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    restaurantLatitude.setText(String.valueOf(locationResult.getLastLocation().getLatitude()));
                                    restaurantLongitude.setText(String.valueOf(locationResult.getLastLocation().getLongitude()));
                                    mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                                }
                            };
                            mLocationRequest = LocationHelper.createLocationRequest();
                            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(RestaurantDetailsAddNewAddNewActivity.this);

                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null).addOnSuccessListener(RestaurantDetailsAddNewAddNewActivity.this).addOnFailureListener(RestaurantDetailsAddNewAddNewActivity.this);

                        }

                        if (!report.getDeniedPermissionResponses().isEmpty()) {
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = new File(RestaurantDetailsAddNewAddNewActivity.this.getExternalCacheDir(), restaurantInfo.id + ".jpeg");
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
            if (addOrEdit.equals(RESTAURANT_EDIT)) {
                getSupportActionBar().setTitle(getString(R.string.restaurant_details_activity_toolbar_title));
            } else {
                getSupportActionBar().setTitle(getString(R.string.restaurant_details_activity_toolbar_title_add));
            }
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showDataToUser() {
        restauantName.setText(restaurantInfo.name);
        restauantName.setSelection(restauantName.getText().length());
        restaurantAddress.setText(restaurantInfo.address);
        restaurantLongitude.setText(String.valueOf(restaurantInfo.longitude));
        restaurantLatitude.setText(String.valueOf(restaurantInfo.latitude));
        setUpImage(restaurantInfo.imageUri);

    }

    private void setUpImage(Uri imageUriArg) {
        Glide.with(this)
                .load(String.valueOf(imageUriArg))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(restaurantImage);
    }

    public void isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.gps_not_found_title);
            builder.setMessage(R.string.gps_not_found_message);
            builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
            builder.setNegativeButton(R.string.no, null);
            builder.create().show();
            return;
        }
    }

    @Override
    public void restaurantDataUpdated() {
        setResult(Activity.RESULT_OK, new Intent().putExtra(RESTAURANT_UPDATED_ADDED_EXTRA, UPDATED));
        finish();
    }

    @Override
    public void onSuccess(Void aVoid) {
        isLocationEnabled();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void restaurantAdded() {
        setResult(Activity.RESULT_OK, new Intent().putExtra(RESTAURANT_UPDATED_ADDED_EXTRA, ADDED));
        finish();
    }
}
