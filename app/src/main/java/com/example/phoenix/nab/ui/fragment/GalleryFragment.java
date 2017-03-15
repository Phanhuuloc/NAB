package com.example.phoenix.nab.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoenix.nab.R;
import com.example.phoenix.nab.common.IFileHandle;
import com.example.phoenix.nab.common.ReadJsonFile;
import com.example.phoenix.nab.common.Utils;
import com.example.phoenix.nab.data.ImgData;
import com.example.phoenix.nab.ui.adapter.ImageAdapter;
import com.example.phoenix.nab.ui.handler.EndlessRecyclerViewScrollListener;
import com.example.phoenix.nab.ui.presenter.GalleryPresenter;
import com.example.phoenix.nab.ui.view.GalleryView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phoenix on 3/13/17.
 */

public class GalleryFragment extends BaseFragment implements GalleryView, IFileHandle {

    public static final String TAG = GalleryFragment.class.getSimpleName();
    public static final String FILE = "FILE";
    public static final int LIMIT = 10;

    ImageAdapter adapter;
    RecyclerView recyclerView;
    GalleryPresenter presenter;
    private String files;
    private int offset = 0;
    private JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GalleryPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        initialize();
        return rootView;
    }

    private void initialize() {
        offset = 0;
        presenter.setView(this);
        initData();
        initializeRecyclerView();
        bindScrollEvent();
    }

    private void bindScrollEvent() {
        StaggeredGridLayoutManager slm = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(slm) {
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                super.onScrolled(view, dx, dy);
                if (getLastVisibleItemPosition() >= offset + 10) {
                    offset += LIMIT;
                    loadImage(jsonArray, offset);
                }
            }
        });
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
            jsonArray = ReadJsonFile.arrayFrom(files);
            List<ImgData> items = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                items.add(new ImgData());
            }
            adapter.setItems(items);

            loadImage(jsonArray, offset);
        }
    }

    private void loadImage(JSONArray jsonArray, int offset) {
        for (int i = offset; i < offset + LIMIT; i++) {
            try {
                presenter.fetchImage(jsonArray.getString(i), i, 150, 150);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeRecyclerView() {
        adapter = new ImageAdapter(getActivity());
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addImage(Bitmap result, String url, int pos) {
        String key = Utils.generateBitmapKey(url);
        adapter.replace(result, url, pos);
    }

    @Override
    public void showImageError(int pos) {
        adapter.setImageError(pos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
