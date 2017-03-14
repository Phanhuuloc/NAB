package com.example.phoenix.nab.data.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.phoenix.nab.NABApplication;
import com.example.phoenix.nab.data.Download;

import io.reactivex.Observable;

/**
 * Created by Phoenix on 3/11/17.
 */

public interface RestApi {

    Context context = NABApplication.get().getApplicationContext();


    Observable<Download> downloadFile(final String url);

    Observable<Bitmap> fetchImage(final String url);
}
