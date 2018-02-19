package com.task.task.ui.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.task.task.R;
import com.task.task.domain.model.RestaurantInfo;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.activities.BaseActivity;
import com.task.task.utils.BitmapUtils;
import com.task.task.utils.SnackBarHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.task.task.utils.Constants.HomeActivityConstants.UPDATE_RESTAURANT_CODE;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, MapsView, GoogleMap.OnMarkerClickListener {


    @Inject
    StringManager stringManager;

    @Inject
    MapsPresenter presenter;

    @Inject
    MapsRouter router;

    private GoogleMap mMap;

    List<RestaurantInfo> listOfRestaurants = new ArrayList<>();

    public static Intent createIntent(final Context context) {
        return new Intent(context, MapsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpToolbar();
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

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.restaurant_map));
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        presenter.getRestaurants();
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showData(List<RestaurantInfo> restaurantInfo) {
        mMap.clear();


        if (!restaurantInfo.isEmpty()) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurantInfo.get(0).latitude, restaurantInfo.get(0).longitude), 9.0f));
            listOfRestaurants.addAll(restaurantInfo);
            for (RestaurantInfo restaurant : restaurantInfo) {
                Bitmap markerBitmap = null;
                if (restaurant.imageUri != null) {
                    try {
                        markerBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), restaurant.imageUri);
                        markerBitmap = BitmapUtils.scaleBitmap(markerBitmap, 70, 70);

                    } catch (IOException e) {
                        Timber.e(e.getMessage());
                    }

                }

                if (markerBitmap != null) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(restaurant.latitude, restaurant.longitude))
                            .title(String.valueOf(restaurant.id))
                            .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));
                }else{

                }
//                mMap.addMarker(new MarkerOptions().position(new LatLng(restaurant.latitude, restaurant.longitude)).title(String.valueOf(restaurant.id)));
//            mMap.addMarker(new MarkerOptions().position(new LatLng(restaurant.latitude, restaurant.longitude)).title(String.valueOf(restaurant.id)).icon(BitmapDescriptorFactory.fromResource(R.drawable.image_place_holder)));
            }
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        for (RestaurantInfo listOfRestaurant : listOfRestaurants) {
            if (listOfRestaurant.id == Integer.parseInt(marker.getTitle())) {
                router.onRestaurantDetailsOrAddNew(listOfRestaurant);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_RESTAURANT_CODE) {
            if (resultCode == RESULT_OK) {

                SnackBarHelper.setUpSnackBar(this, R.drawable.ic_check_circle_outline,
                        stringManager.getString(R.string.restaurant_details_activity_restaurant_updated),
                        R.color.snackbar_green);

                presenter.getRestaurants();
            }
        }
    }
}
