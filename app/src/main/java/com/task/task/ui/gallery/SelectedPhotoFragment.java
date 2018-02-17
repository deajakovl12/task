package com.task.task.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.task.task.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.task.utils.Constants.SelectedPhotoFragmentConstants.ARGUMENTS;

public class SelectedPhotoFragment extends Fragment {

    private String data;

    SendPhotoInterface sendPhotoInterface;

    private boolean isPhotoClicked = false;

    @BindView(R.id.selected_image)
    ImageView selectedImage;

    @BindView(R.id.layout)
    ConstraintLayout constraintLayout;

    @BindView(R.id.use_this_image)
    ImageView useThisImage;

    public SelectedPhotoFragment() {
    }

    public interface SendPhotoInterface {
        void saveThisPhoto(String imageUri);
    }

    public static SelectedPhotoFragment newIstance(String imageUri) {
        SelectedPhotoFragment selectedPhotoFragment = new SelectedPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENTS, imageUri);
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

        Glide.with(this).load(data).centerCrop().into(selectedImage);

        return v;
    }

    @OnClick(R.id.selected_image)
    public void onSelectedImageClick() {
        if (isPhotoClicked) {
            useThisImage.setVisibility(View.GONE);
            selectedImage.setAlpha(1f);
            isPhotoClicked = false;
        } else {
            isPhotoClicked = true;
            useThisImage.setVisibility(View.VISIBLE);
            selectedImage.setAlpha(0.4f);
        }
    }

    @OnClick(R.id.use_this_image)
    public void useThisImage() {
        sendPhotoInterface.saveThisPhoto(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedImage.setAlpha(1f);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        constraintLayout.removeAllViews();
        data = null;
        System.gc();
        Runtime.getRuntime().gc();
    }
}
