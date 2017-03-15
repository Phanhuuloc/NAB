package com.example.phoenix.nab.data.net;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.phoenix.nab.NABApplication;
import com.example.phoenix.nab.common.IFileHandle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
class ApiConnection implements IFileHandle {
    public static final String TAG = ApiConnection.class.getSimpleName();
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    Context context = NABApplication.get().getApplicationContext();
    private URL url;

    private ApiConnection(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url);
    }

    @Nullable
    Response callFetchImageSync() {
        return downloadFileSyncResponse();
    }

    @Nullable
    public Response callDownloadFile() {
        return downloadFileSyncResponse();
    }

    private Response downloadFileSyncResponse() {
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader("Accept-Encoding", "gzip")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download file: " + response);
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private OkHttpClient createClient() {
        final OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(15000, TimeUnit.MILLISECONDS)
                .build();

        return okHttpClient;
    }

}
