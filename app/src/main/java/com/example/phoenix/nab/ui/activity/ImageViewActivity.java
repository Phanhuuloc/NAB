package com.example.phoenix.nab.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phoenix.nab.R;
import com.example.phoenix.nab.ui.ImageDetailView;
import com.example.phoenix.nab.ui.presenter.ImageViewPresenter;

public class ImageViewActivity extends Activity implements ImageDetailView {
    public static final String TAG = ImageViewActivity.class.getSimpleName();
    public static final String BITMAP = "BITMAP";
    public static final String BITMAP_URL = "BITMAP_URL";
    public static final String KEY_BITMAP = "KEY_BITMAP";
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    ImageView imageView;
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    ImageViewPresenter presenter;
    int mode = NONE;
    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;
    private String keyBitmap;
    private Bitmap bitmaps;
    private String bitmapUrl;
    private LinearLayout progressLayout;
    private TextView progressText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        init();
    }

    private void init() {
        presenter = new ImageViewPresenter();
        presenter.setView(this);
        initData();
        initView();
        bindEvent();
    }

    private void bindEvent() {
        imageView.setOnTouchListener((v, event) -> {

            ImageView view = (ImageView) v;
            dumpEvent(event);

            // Handle touch events here...
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    Log.d(TAG, "mode=DRAG");
                    mode = DRAG;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    Log.d(TAG, "oldDist=" + oldDist);
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                        Log.d(TAG, "mode=ZOOM");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    Log.d(TAG, "mode=NONE");
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        // ...
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY()
                                - start.y);
                    } else if (mode == ZOOM) {
                        float newDist = spacing(event);
                        Log.d(TAG, "newDist=" + newDist);
                        if (newDist > 10f) {
                            matrix.set(savedMatrix);
                            float scale = newDist / oldDist;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
                    break;
            }

            view.setImageMatrix(matrix);
            return true;
        });
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
//            keyBitmap = bundle.getString(KEY_BITMAP);
//            bitmaps = bundle.getParcelable(BITMAP);
            bitmapUrl = bundle.getString(BITMAP_URL);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            presenter.fetchImage(bitmapUrl, width, height);
        }
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image_view);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        progressText = (TextView) findViewById(R.id.progress_text);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void renderImage(Bitmap value) {
        progressLayout.setVisibility(View.GONE);
        imageView.setImageBitmap(value);
    }

    @Override
    public void showImageError() {
        progressBar.setVisibility(View.GONE);
        progressText.setText("Download image error!!!");
    }
}
