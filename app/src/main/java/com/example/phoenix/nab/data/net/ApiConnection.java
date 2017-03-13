package com.example.phoenix.nab.data.net;

import android.content.Context;
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

import okhttp3.Call;
import okhttp3.Callback;
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
    private String res;

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
    String requestSyncCall() {
        connectToApiSync();
        return res;
    }


    private void connectToApiSync() {
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
            this.res = FILE_DOWNLOAD_PATH;

        } catch (IOException e) {
            e.printStackTrace();
        }
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
//            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

//            Download download = new Download();
//            download.setTotalFileSize(totalFileSize);

            if (currentTime > 1000 * timeCount) {

//                download.setCurrentFileSize((int) current);
//                download.setProgress(progress);
//                sendNotification(download);
//                timeCount++;
            }

            output.write(data, 0, count);
        }
//        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();


        return true;
    }

    private void connectToApiAsync() {
        OkHttpClient okHttpClient = this.createClient();
        final Request request = new Request.Builder()
                .url(this.url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Failed to download file: " + response);
                }
                FileOutputStream fos = new FileOutputStream(FILE_DOWNLOAD_PATH);
                fos.write(response.body().bytes());
                fos.close();
            }
        });
    }

    private OkHttpClient createClient() {
        final OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(15000, TimeUnit.MILLISECONDS)
                .build();

        return okHttpClient;
    }

//    public void downloadFileSync(String downloadUrl) throws Exception {
//
//        OkHttpClient client = createClient();
//        Request request = new Request.Builder()
//                .url("http://116.118.113.95:5000/fsdownload/0S628zt2x/JSON files.zip")
//                .build();
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Failed to download file: " + response);
//        }
//        FileOutputStream fos = new FileOutputStream(FILE_PATH);
//        fos.write(response.body().bytes());
//        fos.close();
//    }

//    public void downloadFileAsync(final String downloadUrl) throws Exception {
//        OkHttpClient client = createClient();
//        Request request = new Request.Builder()
//                .url("http://116.118.113.95:5000/fsdownload/0S628zt2x/JSON files.zip")
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Failed to download file: " + response);
//                }
//                FileOutputStream fos = new FileOutputStream(FILE_PATH);
//                fos.write(response.body().bytes());
//                fos.close();
//            }
//        });
//    }


    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }
}
