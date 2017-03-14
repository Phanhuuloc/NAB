package com.example.phoenix.nab.data.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.phoenix.nab.common.IFileHandle;
import com.example.phoenix.nab.data.Download;
import com.example.phoenix.nab.data.exception.NetworkConnectionException;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import io.reactivex.Observable;
import okhttp3.Response;

/**
 * Created by Phoenix on 3/11/17.
 */

public class RestApiImpl implements RestApi, IFileHandle {

    public RestApiImpl() {

    }

    public static RestApi getInstance() {
        return Instance.instance;
    }

    @Override
    public Observable<Bitmap> fetchImage(String url) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    Response response = fetchImageFromApi(url);
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();

                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
                        Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                        emitter.onNext(bitmap);
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

    @Override
    public Observable<Download> downloadFile(String url) {
        return Observable.create(sub -> {
                    InputStream input = null;
                    OutputStream output = null;
                    try {
                        Response response = progressDownloadFileFromApi(url);
                        if (response.isSuccessful()) {
                            input = response.body().byteStream();
                            long fileSize = response.body().contentLength();
                            byte data[] = new byte[1024];

                            output = new FileOutputStream(FILE_DOWNLOAD_PATH);

                            int totalFileSize = (int) (fileSize / (Math.pow(1024, 1)));
                            long total = 0;
                            int count;
                            while ((count = input.read(data)) != -1) {
                                total += count;
                                int progress = (int) ((total * 100) / fileSize);
                                double current = Math.round(total / (Math.pow(1024, 1)));

                                Download download = new Download();
                                download.setTotalFileSize(totalFileSize);
                                download.setProgress(progress);
                                download.setCurrentFileSize((int) current);

                                sub.onNext(download);
                                output.write(data, 0, count);
                            }
                            output.flush();
                            output.close();
                            input.close();
                        }
                    } catch (IOException e) {
                        sub.onError(e);
                    } finally {
                        if (input != null) {
                            try {
                                input.close();
                            } catch (IOException ioe) {
                            }
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                    sub.onComplete();
                }
        );
    }

    private Response progressDownloadFileFromApi(String url) throws MalformedURLException {
        return ApiConnection.createGET(url).callDownloadFile();
    }

    private Response fetchImageFromApi(String url) throws MalformedURLException {
        return ApiConnection.createGET(url).callFetchImageSync();
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


    private static class Instance {
        static final RestApi instance = new RestApiImpl();
    }
}
