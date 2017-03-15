package com.example.phoenix.nab.ui.handler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by ikorn on 3/31/2016.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // lastest visible item position
    private int lastVisibleItemPosition = 0;
    // total item count
    private int firstVisibleItem[];
    private int totalItemCount;
    private int visibleItemCount;

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mStaggeredGridLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * mStaggeredGridLayoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener() {
        super();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int[] lastVisibleItemPositions = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
        lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        visibleItemCount = view.getChildCount();
        totalItemCount = mStaggeredGridLayoutManager.getItemCount();
        firstVisibleItem = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null);

        // same code as before
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public int getLastVisibleItemPosition() {
        return lastVisibleItemPosition;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public int getVisibleItemCount() {
        return visibleItemCount;
    }

    public int[] getFirstVisibleItem() {
        return firstVisibleItem;
    }
}