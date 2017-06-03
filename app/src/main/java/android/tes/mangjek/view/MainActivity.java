package android.tes.mangjek.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.tes.mangjek.R;
import android.tes.mangjek.utils.Constants;
import android.tes.mangjek.utils.GetNearbyPlacesData;
import android.tes.mangjek.utils.Utils;
import android.tes.mangjek.utils.locationutil.LocationProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button restaurant, school;
    Object[] DataTransfer;
    String url;
    GetNearbyPlacesData getNearbyPlacesData;
    LocationProvider provider;
    LatLng latLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurant = (Button) findViewById(R.id.button2);
        school = (Button) findViewById(R.id.button3);
        restaurant.setOnClickListener(this);
        school.setOnClickListener(this);
        provider = new LocationProvider(this,null);
        checkGPS();
    }

    public void checkGPS() {
        latLng = provider.getLocation();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda mematikan GPS, Silahkan aktifkan GPS Anda?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        if (latLng == null) {
            alert.show();
        } else {
            alert.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkGPS();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                getData("restaurant");
                break;
            case R.id.button3:
                getData("school");
                break;
        }
    }

    private void getData(String s) {
        if (Utils.isNetworkConnected(this)) {
            Double latitude = latLng.latitude;
            Double longitude = latLng.longitude;
//            url = getUrl(-2.964567, 104.764678, s);
            url = getUrl(latitude, longitude, s);
            DataTransfer = new Object[1];
            DataTransfer[0] = url;
            getNearbyPlacesData = new GetNearbyPlacesData(this);
            getNearbyPlacesData.execute(DataTransfer);
        } else {
            Utils.toastShort(this, "Tidak ada koneksi internet");
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 10000);//in meters
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + Constants.KEY);
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());

//        "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0&reference=CmRRAAAAjkeewS5yIAYGrcd7nEUEPGXmkub9fnD4O9u4a50G81Eaub8DUrOQAn7QC3cuHh0HH-i11eAbfuwis9UGzGVYXaslHdqGLQ8H4noMWZ-tQNSpfK_tx3-b8vsO8AE4-40cEhDlyFAzR0t5uNgHBUHkgTP6GhQo2XuYPf0ZlYSo85tRURFoWERNDQ&sensor=true";

    }
}
