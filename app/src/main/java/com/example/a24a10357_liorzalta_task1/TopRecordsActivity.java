package com.example.a24a10357_liorzalta_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.a24a10357_liorzalta_task1.Fragments.MapFragment;
import com.example.a24a10357_liorzalta_task1.Fragments.RecordsFragment;
import com.example.a24a10357_liorzalta_task1.Interfaces.Callback_recordClicked;

public class TopRecordsActivity extends AppCompatActivity {

    private FrameLayout topRecords_LBL_records;
    private FrameLayout topRecords_LBL_map;
    private RecordsFragment recordsFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_records);

        initViews();

        getSupportFragmentManager().beginTransaction().add(R.id.topRecords_LBL_records, recordsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.topRecords_LBL_map, mapFragment).commit();
    }

    private void initViews() {
        mapFragment = new MapFragment();
        recordsFragment = new RecordsFragment();
        recordsFragment.setContext(this);
        recordsFragment.setCallbackRecordClicked(new Callback_recordClicked() {
            @Override
            public void recordClicked(double lat, double lon) {
                mapFragment.find(lat,lon);
            }
        });
    }


}