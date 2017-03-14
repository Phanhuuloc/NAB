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

    public void fetchImage(String url) {
        fetchImageUseCase.execute(new GetBrandIndexObserver(), FetchImageUseCase.Params.forFetchImg(url));
    }

    private class GetBrandIndexObserver extends DefaultObserver<Bitmap> {
        @Override
        public void onNext(Bitmap result) {
        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable exception) {

        }
    }
}
