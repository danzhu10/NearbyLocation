package android.tes.mangjek.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.tes.mangjek.R;
import android.tes.mangjek.model.PlaceData;
import android.tes.mangjek.utils.locationutil.LocationListener;
import android.tes.mangjek.utils.locationutil.LocationProvider;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, RoutingListener, LocationListener {

    private GoogleMap mMap;
    PlaceData placeData;
    Double latitude, longitude;
    LatLng end, start;
    LocationProvider provider;
    ProgressDialog dialog;
    private List<Polyline> polylines;
    ArrayList<PlaceData> placeDataList;
    int code;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle bundle = getIntent().getExtras();
        placeData = (PlaceData) bundle.getSerializable("place");
        placeDataList = (ArrayList<PlaceData>) bundle.getSerializable("data");
        polylines = new ArrayList<>();
        dialog = new ProgressDialog(this);
        provider = new LocationProvider(this, this);
        latitude = provider.getLocation().latitude;
        longitude = provider.getLocation().longitude;
        Log.d("latlong", "onCreate: " + latitude + " " + longitude);
//        start = new LatLng(-2.964567, 104.764678);//-2.964567, 104.764678 -6.310272, 106.756980
        start = new LatLng(latitude, longitude);

        code = bundle.getInt("code");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void createRoute() {
        dialog.show();
        end = new LatLng(Double.valueOf(placeData.getLatitud()), Double.valueOf(placeData.getLongitude()));
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(start, end)
                .build();
        routing.execute();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(latitude, longitude));
        builder.include(start);

        CameraUpdate center = CameraUpdateFactory.newLatLngBounds(builder.build(), 48);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (code == 1) {
            mMap.setMyLocationEnabled(true);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(start).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.addMarker(new MarkerOptions().position(start).icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue)));
            ShowNearbyPlaces(placeDataList);
        } else {
            mMap.setMyLocationEnabled(true);
            createRoute();
        }
    }

    private void ShowNearbyPlaces(ArrayList<PlaceData> placeDataList) {
        for (int i = 0; i < placeDataList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            PlaceData placeData = placeDataList.get(i);
            LatLng latLng = new LatLng(Double.valueOf(placeData.getLatitud()), Double.valueOf(placeData.getLongitude()));
            markerOptions.position(latLng);
            markerOptions.title(placeData.getPlaceName() + " : " + placeData.getVicinity());
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        dialog.hide();
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimary, R.color.colorAccent, R.color.primary_dark_material_light};

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int x) {
        dialog.hide();
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        mMap.moveCamera(center);

        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(5 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        mMap.addMarker(options);
    }

    @Override
    public void onRoutingCancelled() {

    }

    int count = 1;

    @Override
    public void locationUpdate(Location location) {
        if (placeData != null) {
//                Utils.toastShort(this, "Ganti lokasi......");
            Location loc1 = new Location(LocationManager.GPS_PROVIDER);
            loc1.setLatitude(Double.valueOf(placeData.getLatitud()));
            loc1.setLongitude(Double.valueOf(placeData.getLongitude()));
//            loc1.setLatitude(-6.309173);
//            loc1.setLongitude(106.754376);
            float radius = 10; //in meters

            Location currentLocation = new Location(LocationManager.GPS_PROVIDER);
            currentLocation.setLatitude(location.getLatitude());
            currentLocation.setLongitude(location.getLongitude());
            float distance = loc1.distanceTo(currentLocation);

            if (count == 1) {
                if (distance < radius) {
                    count++;
                    Notification noti = new Notification.Builder(this)
                            .setTicker("ifff")
                            .setContentTitle("Pesan")
                            .setContentText("Anda sudah sampai tujuan")
                            .setWhen(System.currentTimeMillis())
                            .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                            .setSmallIcon(R.mipmap.ic_launcher).build();
                    noti.flags = Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, noti);
                }
            }
        }
    }

}
