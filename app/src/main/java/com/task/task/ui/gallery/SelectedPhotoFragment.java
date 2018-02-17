package com.task.task.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.task.task.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedPhotoFragment extends Fragment {

    private static final String ARGUMENTS = "arguments";
    private String data;
    Bitmap canvasImage;

    SendPhotoInterface sendPhotoInterface;

    private boolean isPhotoClicked = false;

    @BindView(R.id.selected_image)
    ImageView selectedImage;

    @BindView(R.id.layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.use_this_image)
    ImageView useThisImage;

    public SelectedPhotoFragment() {
    }

    public interface SendPhotoInterface {

        void saveThisPhoto();
    }

    public static SelectedPhotoFragment newIstance(String example_argument) {
        SelectedPhotoFragment selectedPhotoFragment = new SelectedPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENTS, example_argument);
        selectedPhotoFragment.setArguments(args);
        return selectedPhotoFragment;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            sendPhotoInterface = (SendPhotoInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selected_photo_fragment, container, false);

        ButterKnife.bind(this, v);

        data = getArguments().getString(ARGUMENTS);


        Glide
                .with(this)
                .load(data)
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(300, 300) {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        selectedImage.setImageBitmap(resource);
                        canvasImage = resource;
                    }
                });

        selectedImage.setOnClickListener(view -> {
            if (isPhotoClicked) {
                useThisImage.setVisibility(View.GONE);
                selectedImage.setAlpha(1f);
                isPhotoClicked = false;
            } else {
                isPhotoClicked = true;
                useThisImage.setVisibility(View.VISIBLE);
                selectedImage.setAlpha(0.4f);
            }
        });

        useThisImage.setOnClickListener(view -> sendPhotoInterface.saveThisPhoto());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedImage.setAlpha(1f);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        relativeLayout.removeAllViews();
        data = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
}
