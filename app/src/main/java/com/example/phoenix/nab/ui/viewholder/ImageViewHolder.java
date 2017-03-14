package com.example.phoenix.nab.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.phoenix.nab.R;

/**
 * Created by Phoenix on 3/14/17.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgItem;
    public LinearLayout imgLayout;

    public ImageViewHolder(View itemView) {
        super(itemView);
        imgItem = (ImageView) itemView.findViewById(R.id.img_item);
        imgLayout = (LinearLayout) itemView.findViewById(R.id.img_layout);
    }
}
