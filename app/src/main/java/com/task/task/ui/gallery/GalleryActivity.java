package com.task.task.ui.gallery;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.task.task.R;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GalleryActivity extends BaseActivity implements GalleryView, SelectedPhotoListener, SelectedPhotoFragment.SendPhotoInterface {

    @Inject
    GalleryActivityPresenter presenter;

    @BindView(R.id.take_or_pick_a_photo_activity_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.photo_recycler_view)
    RecyclerView photoRecyclerView;

    @BindView(R.id.image_view_pager)
    ViewPager imageViewPager;

    private static final int OFFSET_TO_CENTER_IMAGE = -70;
    private static final int MOVE_TO_POSITION_TO_CENTER_SELECTED_IMAGE = 2;
    List<String> imageList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    int positionInList;
    GalleryPhotoRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setUpActionBar();
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), imageList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(positionInList);
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void saveThisPhoto() {
        Toast.makeText(this, "saving", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getImagesPath(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.dispose();
    }

    @Override
    public void showImagesPath(final List<String> imageList) {
        this.imageList = imageList;

        positionInList = 0;

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        photoRecyclerView.setHasFixedSize(true);
        photoRecyclerView.setLayoutManager(layoutManager);

        checkPositionOffSelectedImage(positionInList);
        adapter = new GalleryPhotoRecyclerViewAdapter(GalleryActivity.this, imageList, this, positionInList);

        photoRecyclerView.setAdapter(adapter);

        setupViewPager(imageViewPager);

        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                positionInList = position;
                checkPositionOffSelectedImage(position);
                adapter.changePosition(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkPositionOffSelectedImage(int positionInList) {
        if (positionInList < 2) {
            layoutManager.scrollToPosition(positionInList);
        } else {
            final float scale = GalleryActivity.this.getResources().getDisplayMetrics().density;
            int pixels = (int) (OFFSET_TO_CENTER_IMAGE * scale + 0.5f);
            layoutManager.scrollToPositionWithOffset(positionInList - MOVE_TO_POSITION_TO_CENTER_SELECTED_IMAGE, pixels);
        }
    }

    @Override
    public void onClicked(final int position) {
        positionInList = position;
        imageViewPager.setCurrentItem(position);
    }
}
