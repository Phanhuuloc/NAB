package com.example.phoenix.nab.ui;

import android.graphics.Bitmap;

/**
 * Created by Ikorn Solutions Ltd,.. on 3/15/2017.
 */
public interface ImageDetailView {
    void renderImage(Bitmap value);

    void showImageError();
}
