package com.example.phoenix.nab.ui.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.phoenix.nab.domain.interactor.DefaultObserver;
import com.example.phoenix.nab.domain.interactor.FetchImageUseCase;
import com.example.phoenix.nab.ui.ImageDetailView;

/**
 * Created by Ikorn Solutions Ltd,.. on 3/15/2017.
 */

public class ImageViewPresenter implements Presenter {
    public static final String TAG = ImageViewPresenter.class.getSimpleName();
    private final FetchImageUseCase fetchImageUseCase;
    private ImageDetailView view;

    public ImageViewPresenter() {
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

    public void fetchImage(String bitmapUrl, int width, int height) {
        fetchImageUseCase.execute(new GetImageObserver(),
                FetchImageUseCase.Params.forFetchImg(bitmapUrl, width, height));
    }

    public void setView(ImageDetailView view) {
        this.view = view;
    }

    private class GetImageObserver extends DefaultObserver<Bitmap> {
        @Override
        public void onNext(Bitmap value) {
            Log.i(TAG, "Load bitmap:" + value.toString());
            view.renderImage(value);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.toString());
            view.showImageError();
        }

        @Override
        public void onComplete() {

        }
    }
}
