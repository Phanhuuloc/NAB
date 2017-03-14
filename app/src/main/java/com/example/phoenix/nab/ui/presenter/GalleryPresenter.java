package com.example.phoenix.nab.ui.presenter;

import android.graphics.Bitmap;

import com.example.phoenix.nab.domain.interactor.DefaultObserver;
import com.example.phoenix.nab.domain.interactor.FetchImageUseCase;
import com.example.phoenix.nab.ui.view.GalleryView;

/**
 * Created by Phoenix on 3/10/17.
 */

public class GalleryPresenter implements Presenter {
    private final FetchImageUseCase fetchImageUseCase;
    private GalleryView view;

    public GalleryPresenter() {
        this.fetchImageUseCase = new FetchImageUseCase();
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

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void fetchImage(String url, int pos) {
        fetchImageUseCase.execute(new GetBrandIndexObserver(url, pos), FetchImageUseCase.Params.forFetchImg(url));
    }

    private class GetBrandIndexObserver extends DefaultObserver<Bitmap> {
        private final String url;
        private final int pos;

        public GetBrandIndexObserver(String url, int pos) {
            this.url = url;
            this.pos = pos;
        }

        @Override
        public void onNext(Bitmap result) {
            view.addImage(result, url, pos);
        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable exception) {

        }
    }
}
