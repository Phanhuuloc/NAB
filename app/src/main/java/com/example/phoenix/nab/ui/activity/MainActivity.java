package com.example.phoenix.nab.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phoenix.nab.R;
import com.example.phoenix.nab.common.Decompress;
import com.example.phoenix.nab.ui.presenter.DownloadFilePresenter;
import com.example.phoenix.nab.ui.view.DownloadFileView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DownloadFileView {
    public static final String TAG = MainActivity.class.getSimpleName();

    DownloadFilePresenter presenter;
    @BindView(R.id.url_text)
    EditText urlText;
    @BindView(R.id.btn_download)
    AppCompatButton btnDownload;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress_text)
    TextView progressText;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private String file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initPresenter();
        initView();
    }

    private void initPresenter() {
        presenter = new DownloadFilePresenter();
        presenter.setView(this);
    }

    private void initView() {

    }

    @OnClick(R.id.btn_download)
    public void onClick() {
        String url = urlText.getText().toString();
        presenter.downloadFile(url);
    }

    @Override
    public void handleZipFile(String result) {
        this.file = result;
        if (isStoragePermissionGranted()) {
            Decompress unzip = new Decompress(result, Environment.getExternalStorageDirectory().getPath());
            unzip.unzip();
        } else {
            Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            Decompress unzip = new Decompress(this.file, Environment.getExternalStorageDirectory().getPath());
            unzip.unzip();
        }
    }
}
