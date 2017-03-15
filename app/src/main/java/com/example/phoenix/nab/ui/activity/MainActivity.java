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
import com.example.phoenix.nab.common.IFileHandle;
import com.example.phoenix.nab.common.ReadJsonFile;
import com.example.phoenix.nab.data.Download;
import com.example.phoenix.nab.ui.presenter.DownloadFilePresenter;
import com.example.phoenix.nab.ui.view.DownloadFileView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DownloadFileView, IFileHandle {
    public static final String TAG = MainActivity.class.getSimpleName();

    DownloadFilePresenter presenter;
    EditText urlText;
    AppCompatButton btnDownload;
    ProgressBar progress;
    TextView progressText;
    CoordinatorLayout coordinatorLayout;
    private String file;

    public static List<File> collectFiles(File directory) {
        long length = 0;
        List<File> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                list.add(file);
            } else {
                list.addAll(collectFiles(file));
            }
        }
        return list;
    }

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
        bindEvent();
    }

    private void bindEvent() {
        btnDownload.setOnClickListener(view -> {
            String url = urlText.getText().toString();
            presenter.downloadFile(url);
        });
    }

    private void initPresenter() {
        presenter = new DownloadFilePresenter();
        presenter.setView(this);
    }

    private void initView() {
        urlText = (EditText) findViewById(R.id.url_text);
        btnDownload = (AppCompatButton) findViewById(R.id.btn_download);
        progress = (ProgressBar) findViewById(R.id.progress);
        progressText = (TextView) findViewById(R.id.progress_text);
    }

    @Override
    public void handleZipFile(String zipFile) {
        this.file = zipFile;
//        unzipFile(file);

        File fileDirectory = new File(REAL_FILE_UNZIP_PATH);
        List<File> files = collectFiles(fileDirectory);

        int count = 0;
        HashMap<String, String> map = new HashMap<>();
        for (File file : files) {
            count++;
            String fileName = file.getName();
            String fromFile = ReadJsonFile.read(file);
            Log.i(TAG, String.format("file %d: %s", count, fromFile));
            map.put(fileName, fromFile);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(GalleryActivity.EXTRA_TAB, count);
        bundle.putSerializable(GalleryActivity.EXTRA_FILE_MAP, map);
        BaseActivity.start(this, GalleryActivity.class, bundle);
    }

    @Override
    public void updateProgress(Download download) {
        int progressVal = download.getProgress();
        progress.setProgress(progressVal);
        if (progressVal == 100) {
            progressText.setText("File Download Complete");
            handleZipFile(FILE_DOWNLOAD_PATH);
        } else {
            progressText.setText(String.format("Downloaded (%d/%d) KB", download.getCurrentFileSize(), download.getTotalFileSize()));
        }
    }

    private void unzipFile(String zipFile) {
        if (isStoragePermissionGranted()) {
            Decompress unzip = new Decompress(zipFile, FILE_UNZIP_PATH);
            unzip.unzip();
        } else {
            Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
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
        } else {
            //permission is automatically granted on sdk<23 upon installation
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
