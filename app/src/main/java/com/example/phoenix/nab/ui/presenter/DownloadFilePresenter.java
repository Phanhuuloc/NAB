package com.example.phoenix.nab.ui.presenter;

import com.example.phoenix.nab.data.Download;
import com.example.phoenix.nab.domain.interactor.DefaultObserver;
import com.example.phoenix.nab.domain.interactor.DownloadFileUseCase;
import com.example.phoenix.nab.ui.view.DownloadFileView;

/**
 * Created by Phoenix on 3/11/17.
 */
public class DownloadFilePresenter implements Presenter {
    private final DownloadFileUseCase downloadFileUseCase;
    private DownloadFileView view;

    public DownloadFilePresenter() {
        this.downloadFileUseCase = new DownloadFileUseCase();
    }

    public void setView(DownloadFileView view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void downloadFile(String url) {
        downloadFileUseCase.execute(new GetBrandIndexObserver(),
                DownloadFileUseCase.Params.forDownload(url));
    }

    private class GetBrandIndexObserver extends DefaultObserver<Download> {
        @Override
        public void onNext(Download result) {
            DownloadFilePresenter.this.view.updateProgress(result);
        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable exception) {
//            DownloadFilePresenter.this.view.handleZipFile(result);
        }
    }
}
