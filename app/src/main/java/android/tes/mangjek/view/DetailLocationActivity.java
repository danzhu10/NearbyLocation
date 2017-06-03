package android.tes.mangjek.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.tes.mangjek.R;
import android.tes.mangjek.implement.DetailListImpl;
import android.tes.mangjek.model.PlaceData;
import android.tes.mangjek.model.PlaceDetails;
import android.tes.mangjek.presenter.CallBackView;
import android.tes.mangjek.service.UserNotification;
import android.tes.mangjek.utils.Constants;
import android.tes.mangjek.view.adapter.NearbyPlaceAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class DetailLocationActivity extends AppCompatActivity {

    TextView name,address,latlong,phone;
    Button getThere;
    PlaceData placeData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        placeData = (PlaceData) bundle.getSerializable("data");
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        latlong = (TextView)findViewById(R.id.latlong);
        getThere = (Button) findViewById(R.id.getThere);
        phone = (TextView)findViewById(R.id.phone);
        getThere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MapsActivity.class)
                .putExtra("place",placeData)
                .putExtra("code",2));
            }
        });
        getDetails();
    }

    private void getDetails() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Mohon tunggu...");
        dialog.show();
        DetailListImpl detailList = new DetailListImpl(new CallBackView() {
            @Override
            public void onCallError(Throwable t) {
                dialog.dismiss();
                Log.e("error", "onCallError: "+t.getMessage() );
            }

            @Override
            public void onCallSuccess(Object object) {
                dialog.dismiss();
                PlaceDetails placeDetails = (PlaceDetails) object;
                name.setText(placeData.getPlaceName());
                if (TextUtils.isEmpty(placeDetails.getResult().getFormatted_phone_number())){
                    phone.setText("Tidak ada");
                }else {
                    phone.setText(placeDetails.getResult().getFormatted_phone_number());
                }
                address.setText(placeDetails.getResult().getVicinity());
                latlong.setText("Latitude: "+placeData.getLatitud()+" Longitude :"+placeData.getLongitude());
            }
        });
        detailList.onGetDetail(Constants.KEY,placeData.getReference());

    }

}
