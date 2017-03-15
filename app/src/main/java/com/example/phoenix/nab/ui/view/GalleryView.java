package com.example.phoenix.nab.ui.view;

import android.graphics.Bitmap;

/**
 * Created by Phoenix on 3/13/17.
 */

public interface GalleryView {
    void addImage(Bitmap result, String url, int pos);

    void showImageError(int pos);
}
