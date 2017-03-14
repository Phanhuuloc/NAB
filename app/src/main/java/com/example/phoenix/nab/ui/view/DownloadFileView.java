package com.example.phoenix.nab.ui.view;

import com.example.phoenix.nab.data.Download;

/**
 * Created by Phoenix on 3/11/17.
 */
public interface DownloadFileView {
    void handleZipFile(String result);

    void updateProgress(Download result);
}
