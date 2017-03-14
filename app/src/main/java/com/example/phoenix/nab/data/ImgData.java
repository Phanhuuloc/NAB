package com.example.phoenix.nab.data;

import android.graphics.Bitmap;

/**
 * Created by Phoenix on 3/14/17.
 */

public class ImgData {
    Bitmap bitmap;
    Download status;
    boolean isError;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
}
