package ru.beetlesoft.clientapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.ui.holders.ActionViewHolder;

public class ActionAdapter extends RecyclerView.Adapter<ActionViewHolder>{

    private List<HashMap<Integer, String>> mapAction;
    private Context context;

    private OnRvItemClickListener onRvItemClickListener;

    public ActionAdapter(Context context, List<HashMap<Integer, String>> actions){
        this.mapAction = actions;
        this.context = context;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRvItemClickListener.onClick(view);
            }
        });
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        int key = (int) mapAction.get(position).keySet().toArray()[0];
        holder.ivAction.setImageDrawable(ContextCompat.getDrawable(context, key));
        holder.tvAction.setText(mapAction.get(position).get(key));
    }

    @Override
    public int getItemCount() {
        return mapAction.size();
    }

    public interface OnRvItemClickListener {
        void onClick(View view);
    }

    public void setRvItemClickListener(OnRvItemClickListener listener) {
        this.onRvItemClickListener = listener;
    }
}
