package com.example.phoenix.nab.data.repository;

import android.graphics.Bitmap;

import com.example.phoenix.nab.data.Download;
import com.example.phoenix.nab.data.net.RestApi;

import io.reactivex.Observable;

/**
 * Created by Phoenix on 3/11/17.
 */

public class ApiMediator {
    private final RestApi restApi;

    /**
     * @param restApi The {@link RestApi} implementation to use.
     */
    public ApiMediator(RestApi restApi) {
        this.restApi = restApi;
    }

    public Observable<Download> downloadFile(final String url) {
        return this.restApi.downloadFile(url);
    }

    public Observable<Bitmap> fetchImage(String url, int reqWidth, int reqHeight) {
        return this.restApi.fetchImage(url, reqWidth, reqHeight);
    }
}
