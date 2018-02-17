package com.task.task.utils;


public class Constants {

    public interface HomeActivityConstants {

        String DATA_INFO = "data_info";

        String DATA_DOWNLOADED = "data_downloaded";
        String DATA_LOADED_FROM_DB = "data_loaded_from_db";
        String DATA_NOT_DOWNLOADED_NO_INTERNET = "no_internet";
        String DATA_ERROR_DOWNLOADING = "data_error_downloading";
    }

    public interface SnackBarConstants {
        int ICON_SIZE = 24;
        int ICON_PADDING = 8;
    }

    public interface RestaurantDetailsActivityConstants {
        String RESTAURANT_DETAILS_INFO = "restaurant_details_info";

    }

    public interface GalleryActivityConstants {
        int OFFSET_TO_CENTER_IMAGE = -35;
        int MOVE_TO_POSITION_TO_CENTER_SELECTED_IMAGE = 2;
    }

    public interface SelectedPhotoFragmentConstants {
        String ARGUMENTS = "image_arguments";
    }

    public interface GalleryPhotoRecyclerViewAdapterConstants {
        int FOCUSED_PHOTO = 1;
        int NOT_FOCUSED_PHOTO = 2;
    }
}
