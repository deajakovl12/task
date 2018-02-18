package com.task.task.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.task.task.R;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.ui.base.activities.BaseActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.task.task.injection.module.ManagerModule.HORIZONTAL_LL_MANAGER;
import static com.task.task.utils.Constants.GalleryActivityConstants.MOVE_TO_POSITION_TO_CENTER_SELECTED_IMAGE;
import static com.task.task.utils.Constants.GalleryActivityConstants.OFFSET_TO_CENTER_IMAGE;
import static com.task.task.utils.Constants.RestaurantDetailsActivityConstants.PICK_GALLERY_IMAGE_EXTRA;


public class GalleryActivity extends BaseActivity implements GalleryView, SelectedPhotoFragment.SendPhotoInterface, GalleryPhotoRecyclerViewAdapter.Listener {

    @Inject
    GalleryActivityPresenter presenter;

    @Inject
    @Named(HORIZONTAL_LL_MANAGER)
    LinearLayoutManager layoutManager;

    @Inject
    GalleryPhotoRecyclerViewAdapter adapter;

    @BindView(R.id.take_or_pick_a_photo_activity_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.photo_recycler_view)
    RecyclerView photoRecyclerView;

    @BindView(R.id.image_view_pager)
    ViewPager imageViewPager;

    int positionInList;

    public static Intent createIntent(final Context context) {
        return new Intent(context, GalleryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setUpActionBar();
    }


    private void setupViewPager(ViewPager viewPager, List<String> imageList) {
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
    public void saveThisPhoto(String imageUri) {
        setResult(Activity.RESULT_OK, new Intent().putExtra(PICK_GALLERY_IMAGE_EXTRA, imageUri));
        finish();
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
        positionInList = 0;

        photoRecyclerView.setHasFixedSize(true);
        photoRecyclerView.setLayoutManager(layoutManager);

        checkPositionOffSelectedImage(positionInList);

        adapter.setContext(this);
        adapter.setListener(this);
        adapter.setData(imageList);
        adapter.changePosition(positionInList);
        photoRecyclerView.setAdapter(adapter);

        setupViewPager(imageViewPager, imageList);

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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onImageClicked(int position) {
        positionInList = position;
        imageViewPager.setCurrentItem(position);
    }
}
