package com.example.phoenix.nab.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoenix.nab.R;
import com.example.phoenix.nab.common.ReadJsonFile;
import com.example.phoenix.nab.ui.presenter.GalleryPresenter;
import com.example.phoenix.nab.ui.view.GalleryView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Phoenix on 3/13/17.
 */

public class GalleryFragment extends BaseFragment implements GalleryView {

    public static final String TAG = GalleryFragment.class.getSimpleName();
    public static final String FILE = "FILE";

    GalleryPresenter presenter;
    private String files;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GalleryPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        initialize();
        return rootView;
    }

    private void initialize() {
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            files = bundle.getString(FILE);
            Log.i(TAG, files);
        }
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (null != files) {
            JSONArray jsonArray = ReadJsonFile.arrayFrom(files);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    presenter.fetchImage(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
