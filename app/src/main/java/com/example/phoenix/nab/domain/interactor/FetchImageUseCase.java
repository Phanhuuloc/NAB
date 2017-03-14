package com.example.phoenix.nab.domain.interactor;

import android.graphics.Bitmap;

import com.example.phoenix.nab.common.AppUtils;
import com.example.phoenix.nab.data.net.RestApiImpl;
import com.example.phoenix.nab.data.repository.ApiMediator;

import io.reactivex.Observable;

/**
 * Created by Phoenix on 3/13/17.
 */

public class FetchImageUseCase extends UseCase<Bitmap, FetchImageUseCase.Params> {

    private final ApiMediator apiMediator;

    public FetchImageUseCase() {
        super();
        this.apiMediator = new ApiMediator(RestApiImpl.getInstance());
    }

    @Override
    Observable<Bitmap> buildUseCaseObservable(Params params) {
        AppUtils.checkNotNull(params);
        return apiMediator.fetchImage(params.url);
    }

    public static class Params {
        private final String url;

        public Params(String url) {
            this.url = url;
        }

        public static Params forFetchImg(String url) {
            return new Params(url);
        }
    }
}
