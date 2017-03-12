package com.example.phoenix.nab.domain.interactor;

import com.example.phoenix.nab.common.AppUtils;
import com.example.phoenix.nab.data.net.RestApiImpl;
import com.example.phoenix.nab.data.repository.ApiMediator;

import io.reactivex.Observable;

/**
 * Created by Phoenix on 3/11/17.
 */

public class DownloadFileUseCase extends UseCase<String, DownloadFileUseCase.Params> {
    private final ApiMediator apiMediator;

    public DownloadFileUseCase() {
        super();
        this.apiMediator = new ApiMediator(RestApiImpl.getInstance());
    }

    @Override
    Observable<String> buildUseCaseObservable(Params params) {
        AppUtils.checkNotNull(params);
        return apiMediator.downloadFile(params.url);
    }

    public static class Params {
        private final String url;

        public Params(String url) {
            this.url = url;
        }

        public static Params forDownload(String url) {
            return new Params(url);
        }
    }
}
