package com.task.task.ui.gallery;

import android.app.Activity;

public interface GalleryActivityPresenter {

    void setView(GalleryView view);

    void getImagesPath(Activity activity);

    void dispose();
}
