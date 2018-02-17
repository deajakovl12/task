package com.task.task.ui.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.task.task.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.task.utils.Constants.GalleryPhotoRecyclerViewAdapterConstants.FOCUSED_PHOTO;
import static com.task.task.utils.Constants.GalleryPhotoRecyclerViewAdapterConstants.NOT_FOCUSED_PHOTO;

public class GalleryPhotoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Listener {

        GalleryPhotoRecyclerViewAdapter.Listener EMPTY = (position) -> {
        };

        void onImageClicked(int position);
    }

    private List<String> photoList = new ArrayList<>();
    private Context context;
    private GalleryPhotoRecyclerViewAdapter.Listener listener = GalleryPhotoRecyclerViewAdapter.Listener.EMPTY;
    int positionInList;


    @Override
    public int getItemViewType(int position) {
        if (positionInList == position) {
            return FOCUSED_PHOTO;
        } else {
            return NOT_FOCUSED_PHOTO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case NOT_FOCUSED_PHOTO:
                View layoutViewNotFocused = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_not_focused_item, null);
                PhotoViewHolder pvh = new PhotoViewHolder(layoutViewNotFocused);
                holder = pvh;
                break;
            case FOCUSED_PHOTO:
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_focused_item, null);
                FocusedPhotoViewHolder fpvh = new FocusedPhotoViewHolder(layoutView);
                holder = fpvh;
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case FOCUSED_PHOTO:
                FocusedPhotoViewHolder focusHolder = (FocusedPhotoViewHolder) holder;
                showItemsFocusHolder(focusHolder, position);
                break;
            case NOT_FOCUSED_PHOTO:
                PhotoViewHolder notFocusHolder = (PhotoViewHolder) holder;
                showItemsNotFocusHolder(notFocusHolder, position);
                break;
        }
    }

    public void setData(final List<String> data) {
        if (data != null) {
            photoList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void changePosition(int pos) {
        this.positionInList = pos;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListener(final GalleryPhotoRecyclerViewAdapter.Listener listener) {
        this.listener = listener != null ? listener : GalleryPhotoRecyclerViewAdapter.Listener.EMPTY;
    }

    private void showItemsFocusHolder(final FocusedPhotoViewHolder holder, int position) {
        Glide.with(context).load(photoList.get(position))
                .placeholder(R.drawable.image_place_holder).centerCrop()
                .into(holder.libPhotoFocused);
    }

    private void showItemsNotFocusHolder(final PhotoViewHolder holder, final int position) {
        Glide.with(context).load(photoList.get(position))
                .placeholder(R.drawable.image_place_holder).centerCrop()
                .into(holder.libPhotoNotFocused);
    }

    @Override
    public int getItemCount() {
        return this.photoList.size();
    }

    public class FocusedPhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lib_photo)
        ImageView libPhotoFocused;

        public FocusedPhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lib_photo)
        ImageView libPhotoNotFocused;

        @OnClick(R.id.whole_layout)
        public void onImageClicked() {
            positionInList = getAdapterPosition();
            listener.onImageClicked(getAdapterPosition());

        }

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
