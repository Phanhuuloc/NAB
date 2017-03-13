package com.example.phoenix.nab.common;

import android.os.Environment;

/**
 * Created by Phoenix on 3/13/17.
 */

public interface IFileHandle {
    String BASE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    String ZIP_EXTENSION = ".zip";
    String FILE_NAME = "images";
    String SEPERATOR = "/";
    String FILE_DOWNLOAD_PATH = BASE_PATH + SEPERATOR + FILE_NAME + ZIP_EXTENSION;
    String FILE_UNZIP_PATH = BASE_PATH + SEPERATOR + FILE_NAME + SEPERATOR;
}
