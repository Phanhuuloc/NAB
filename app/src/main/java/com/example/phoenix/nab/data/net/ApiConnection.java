package com.example.phoenix.nab.data.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.phoenix.nab.NABApplication;
import com.example.phoenix.nab.common.IFileHandle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
class ApiConnection implements Callable<String>, IFileHandle {
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

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string res
     */
    @Nullable
    String callDownloadSync() {
        return downloadFileSync();
    }

    @Nullable
    Bitmap callFetchImageSync() {
        return fetchImageSync();
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

    private String downloadFileSync() {
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

            writeResponseBodyToDisk(response.body());
            Log.v(TAG, response.networkResponse().headers().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FILE_DOWNLOAD_PATH;
    }

    private Bitmap fetchImageSync() {
        Bitmap bitmap = null;
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch image: " + response);
            }

            ResponseBody in = response.body();
            InputStream inputStream = in.byteStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) throws IOException {
        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(FILE_DOWNLOAD_PATH);
        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {
            total += count;
            double current = Math.round(total / (Math.pow(1024, 2)));
            int progress = (int) ((total * 100) / fileSize);
            long currentTime = System.currentTimeMillis() - startTime;
            output.write(data, 0, count);
        }
        output.flush();
        output.close();
        bis.close();


        return true;
    }

    private OkHttpClient createClient() {
        final OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(15000, TimeUnit.MILLISECONDS)
                .build();

        return okHttpClient;
    }

    @Override
    public String call() throws Exception {
        return callDownloadSync();
    }

}
