package com.example.a24a10357_liorzalta_task1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a24a10357_liorzalta_task1.R;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment {

    private MaterialTextView fMap_LBL_map;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        fMap_LBL_map = view.findViewById(R.id.fMap_LBL_map);
    }

    public void find(double lat, double lon){
        fMap_LBL_map.setText(lat + ", " + lon);
    }
}