package com.example.phoenix.nab.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phoenix.nab.R;

/**
 * Created by Phoenix on 3/14/17.
 */

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar progress;
    public TextView progressText;
    public LinearLayout imgLayout;

    public ProgressViewHolder(View itemView) {
        super(itemView);
        progress = (ProgressBar) itemView.findViewById(R.id.progress);
        progressText = (TextView) itemView.findViewById(R.id.progress_text);
        imgLayout = (LinearLayout) itemView.findViewById(R.id.img_layout);
    }
}
