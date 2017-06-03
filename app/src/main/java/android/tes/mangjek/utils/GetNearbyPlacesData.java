package android.tes.mangjek.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.tes.mangjek.model.PlaceData;
import android.tes.mangjek.view.LocationListActivity;
import android.tes.mangjek.view.MapsActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by navneet on 23/7/16.
 */
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private String url;
    private ProgressDialog dialog;
    private Context context;
    private ArrayList<PlaceData> placeDataList = new ArrayList<>();

    public GetNearbyPlacesData(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Mohon tunggu...");
        dialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            url = (String) params[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.hide();
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        placeDataList.clear();
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            String reference = googlePlace.get("reference");

            PlaceData placeData = new PlaceData();
            placeData.setPlaceName(placeName);
            placeData.setVicinity(vicinity);
            placeData.setLatitud(String.valueOf(lat));
            placeData.setLongitude(String.valueOf(lng));
            placeData.setReference(reference);
            placeDataList.add(placeData);
        }
        Log.d("placedata", "ShowNearbyPlaces: " + placeDataList.size());
        context.startActivity(new Intent(context, LocationListActivity.class)
                .putExtra("data", placeDataList));
    }
}
