package com.example.phoenix.nab.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
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
        Decompress unzip = new Decompress(result, Environment.getExternalStorageDirectory().getPath());
        unzip.unzip();
    }
}
