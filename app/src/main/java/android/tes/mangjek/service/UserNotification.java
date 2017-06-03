package android.tes.mangjek.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.tes.mangjek.R;
import android.tes.mangjek.model.PlaceData;
import android.tes.mangjek.utils.locationutil.LocationProvider;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class UserNotification extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    LocationProvider provider;
    PlaceData placeData;

    @Override
    public void onCreate() {
        super.onCreate();
        provider = new LocationProvider(this,null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                placeData = (PlaceData) bundle.getSerializable("place");
            }
        }

        Location loc1 = new Location(LocationManager.GPS_PROVIDER);
//        loc1.setLatitude(Double.valueOf(placeData.getLatitud()));
//        loc1.setLongitude(Double.valueOf(placeData.getLongitude()));
        loc1.setLatitude(-6.309173);
        loc1.setLongitude(106.754376);
        float radius = 2f;

        Location currentLocation = new Location(LocationManager.GPS_PROVIDER);
        currentLocation.setLatitude(provider.getLocation().latitude);
        currentLocation.setLongitude(provider.getLocation().longitude);
//        -6.309173, 106.754376
        float distance = loc1.distanceTo(currentLocation);


        if (distance < radius) {
            Notification noti = new Notification.Builder(this)
                    .setTicker("ifff")
                    .setContentTitle("ifff")
                    .setContentText("ifff")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                    .setSmallIcon(R.mipmap.ic_launcher).build();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, noti);
        } else {
            Notification noti = new Notification.Builder(this)
                    .setTicker("elseee")
                    .setContentTitle("elseee")
                    .setContentText("elseee")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                    .setSmallIcon(R.mipmap.ic_launcher).build();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, noti);
        }
        return START_STICKY;
    }
}
