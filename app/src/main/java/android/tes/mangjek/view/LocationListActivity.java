package android.tes.mangjek.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.tes.mangjek.R;
import android.tes.mangjek.model.PlaceData;
import android.tes.mangjek.view.adapter.NearbyPlaceAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class LocationListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayList<PlaceData> placeDataList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listView = (ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        getData();
        findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MapsActivity.class)
                .putExtra("data",placeDataList)
                .putExtra("code",1));
            }
        });
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        placeDataList = (ArrayList<PlaceData>) bundle.getSerializable("data");
        Log.d("placedata2", "ShowNearbyPlaces: "+placeDataList.size());
        listView.setAdapter(new NearbyPlaceAdapter(this,placeDataList));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PlaceData placeData = (PlaceData) listView.getAdapter().getItem(i);
        startActivity(new Intent(this,DetailLocationActivity.class)
        .putExtra("data",placeData));
    }
}
