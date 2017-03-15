package com.example.phoenix.nab.data;

/**
 * Created by Phoenix on 3/14/17.
 */

public class ImgData {
    String bitmapKey;
    Download status;
    boolean isError;

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
}
