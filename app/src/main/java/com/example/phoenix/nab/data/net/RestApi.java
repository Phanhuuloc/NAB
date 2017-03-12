package com.example.phoenix.nab.data.net;

import android.content.Context;

import com.example.phoenix.nab.NABApplication;

import io.reactivex.Observable;

/**
 * Created by Phoenix on 3/11/17.
 */

public interface RestApi {

    Context context = NABApplication.get().getApplicationContext();


    Observable<String> downloadFile(final String url);
}
