package com.example.phoenix.nab.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.phoenix.nab.data.exception.NetworkConnectionException;

import java.net.MalformedURLException;

import io.reactivex.Observable;

/**
 * Created by Phoenix on 3/11/17.
 */

public class RestApiImpl implements RestApi {

    /**
     * Constructor of the class
     */
    public RestApiImpl() {

    }

    public static RestApi getInstance() {
        return Instance.instance;
    }

    @Override
    public Observable<String> downloadFile(final String url) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String response = downloadFileFromApi(url);
                    if (response != null) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    public String downloadFileFromApi(String url) throws MalformedURLException {
        return ApiConnection.createGET(url).requestSyncCall();

    }

    private static class Instance {
        static final RestApi instance = new RestApiImpl();
    }
}
