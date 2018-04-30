package ru.beetlesoft.clientapp.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.ui.adapters.ActionAdapter;

public class MainFragment extends Fragment {

    @BindView(R.id.rv_action)
    RecyclerView rvAction;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        this.context = this.getActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvAction.setLayoutManager(linearLayoutManager);

        ActionAdapter adapter = new ActionAdapter(context, getActions());
        adapter.setRvItemClickListener(new ActionAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = rvAction.getChildLayoutPosition(view);
                action(itemPosition);
            }
        });
        rvAction.setAdapter(adapter);

        return rootView;
    }

    private List<HashMap<Integer, String>> getActions() {
        List<HashMap<Integer, String>> mapAction = new ArrayList<>();
        mapAction.add(createMap(R.drawable.ic_add_sound, getString(R.string.title_sound)));
        mapAction.add(createMap(R.drawable.ic_add_photo, getString(R.string.title_photo)));
        mapAction.add(createMap(R.drawable.ic_location, getString(R.string.title_geofence)));
        return mapAction;
    }

    private HashMap<Integer, String> createMap(Integer drawableId, String actionText) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(drawableId, actionText);
        return map;
    }

    private void action(int position) {
        Toast.makeText(context, getActions().get(position).values().toString(), Toast.LENGTH_LONG).show();

        switch (position) {
            case 0:
                //sound
                break;
            case 1:
                //foto
                break;
            case 2:
                //geofence
                break;
            default:
                break;

        }
    }
}
