package com.task.task.utils;


public class Constants {

    public interface HomeActivityConstants {

        String DATA_INFO = "data_info";

        String DATA_DOWNLOADED = "data_downloaded";
        String DATA_LOADED_FROM_DB = "data_loaded_from_db";
        String DATA_NOT_DOWNLOADED_NO_INTERNET = "no_internet";
        String DATA_ERROR_DOWNLOADING = "data_error_downloading";

        int UPDATE_RESTAURANT_CODE = 48484;
    }

    public interface SnackBarConstants {
        int ICON_SIZE = 24;
        int ICON_PADDING = 8;
    }

    public interface RestaurantDetailsActivityConstants {
        String RESTAURANT_DETAILS_INFO = "restaurant_details_info";
        String RESTAURANT_ADD_OR_EDIT = "restaurant_add_or_edit";
        String RESTAURANT_EDIT = "restaurant_edit";
        String RESTAURANT_ADD = "restaurant_add";
        int PICK_GALLERY_IMAGE_CODE = 21414;
        String PICK_GALLERY_IMAGE_EXTRA = "image_uri_extra";

        int REQUEST_IMAGE_CAPTURE = 4451;
        int COMPRESS_QUALITY = 100;


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
