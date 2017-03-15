package com.example.phoenix.nab.data;

import android.graphics.Bitmap;

/**
 * Created by Phoenix on 3/14/17.
 */

public class ImgData {
    private String bitmapKey;
    private Bitmap bitmap;
    private Download status;
    private boolean isError;
    private String bitmapUrl;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getBitmapKey() {
        return bitmapKey;
    }

    public void setBitmapKey(String bitmapKey) {
        this.bitmapKey = bitmapKey;
    }

    public Download getStatus() {
        return status;
    }

    public void setStatus(Download status) {
        this.status = status;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getBitmapUrl() {
        return bitmapUrl;
    }

    public void setBitmapUrl(String bitmapUrl) {
        this.bitmapUrl = bitmapUrl;
    }
}
