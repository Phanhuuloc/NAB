package com.example.phoenix.nab.ui.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.phoenix.nab.domain.interactor.DefaultObserver;
import com.example.phoenix.nab.domain.interactor.FetchImageUseCase;
import com.example.phoenix.nab.ui.view.GalleryView;

/**
 * Created by Phoenix on 3/10/17.
 */

public class GalleryPresenter implements Presenter {
    public static final String TAG = GalleryPresenter.class.getSimpleName();
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

    public void fetchImage(String url, int pos, int reqWidth, int reqHeight) {
        fetchImageUseCase.execute(new GetImageObserver(url, pos),
                FetchImageUseCase.Params.forFetchImg(url, reqWidth, reqHeight));
    }

    private class GetImageObserver extends DefaultObserver<Bitmap> {
        private final String url;
        private final int pos;

        public GetImageObserver(String url, int pos) {
            this.url = url;
            this.pos = pos;
        }

        @Override
        public void onNext(Bitmap result) {
            Log.i(TAG, "Load bitmap:" + result.toString());
            view.addImage(result, url, pos);
        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable exception) {
            Log.e(TAG, exception.toString());
            view.showImageError(pos);
        }
    }
}
