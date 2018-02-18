package com.task.task.utils;


import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.androidadvance.topsnackbar.TSnackbar;

import static com.task.task.utils.Constants.SnackBarConstants.ICON_PADDING;
import static com.task.task.utils.Constants.SnackBarConstants.ICON_SIZE;

public class SnackBarHelper {

    public static void setUpSnackBar(Activity activity, int iconImage, String snackText, int backgroundColor) {
        TSnackbar snackbar = TSnackbar
                .make(activity.findViewById(android.R.id.content), snackText, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setIconRight(iconImage, ICON_SIZE);
        snackbar.setIconPadding(ICON_PADDING);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, backgroundColor));
        snackbar.show();
    }
}
