package com.example.phoenix.nab.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoenix.nab.R;
import com.example.phoenix.nab.data.ImgData;
import com.example.phoenix.nab.ui.viewholder.ErrorViewHolder;
import com.example.phoenix.nab.ui.viewholder.ImageViewHolder;
import com.example.phoenix.nab.ui.viewholder.ProgressViewHolder;

import java.util.List;

/**
 * Created by Phoenix on 3/14/17.
 */

public class ImageAdapter extends RecyclerView.Adapter {
    private static final int TYPE_DATA = 0;
    private static final int TYPE_PROGRESS = 1;
    private static final int TYPE_ERROR = 2;
    private final Activity mActivity;
    private final LayoutInflater mInflater;

    private List<ImgData> items;

    public ImageAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        mInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getItemViewType(int position) {
        ImgData item = items.get(position);
        if (item.isError()) {
            return TYPE_ERROR;
        } else if (null != item.getBitmap()) {
            return TYPE_DATA;
        } else return TYPE_PROGRESS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATA) {
            View view = mInflater.inflate(R.layout.image_item, parent, false);
            return new ImageViewHolder(view);
        } else if (viewType == TYPE_PROGRESS) {
            View view = mInflater.inflate(R.layout.image_item_downloading, parent, false);
            return new ProgressViewHolder(view);
        } else if (viewType == TYPE_ERROR) {
            View view = mInflater.inflate(R.layout.image_item_error, parent, false);
            return new ErrorViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.image_item_error, parent, false);
            return new ErrorViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImgData item = items.get(position);
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            viewHolder.imgItem.setImageBitmap(item.getBitmap());
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder viewHolder = (ProgressViewHolder) holder;
            if (item.getStatus() != null) {
                viewHolder.progress.setProgress(item.getStatus().getProgress());
                viewHolder.progressText.setText(String.format(
                        "Downloaded (%d/%d) KB", item.getStatus().getCurrentFileSize(), item.getStatus().getTotalFileSize()));
            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return null == items ? 0 : items.size();
    }

    public void setItems(List<ImgData> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void replace(Bitmap result, int pos) {
        items.get(pos).setBitmap(result);
        notifyDataSetChanged();
    }
}
