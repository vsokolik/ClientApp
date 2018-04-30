package ru.beetlesoft.clientapp.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;

public class ActionViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_action)
    public TextView tvAction;
    @BindView(R.id.iv_action)
    public ImageView ivAction;

    public ActionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
