package com.example.phoenix.nab.data.repository;

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

    public Observable<String> downloadFile(final String url) {
        return this.restApi.downloadFile(url);
//                .doOnNext(s -> {
//                    Decompress unzip = new Decompress(s, Environment.getExternalStorageDirectory().getPath());
//                    unzip.unzip();
//                });
    }

}
