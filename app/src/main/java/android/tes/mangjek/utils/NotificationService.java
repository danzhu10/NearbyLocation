//package android.tes.mangjek.utils;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.tes.mangjek.R;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
///**
// * Created by EduSPOT on 8/15/2016.
// */
//public class NotificationService extends FirebaseMessagingService {
//
//    int index = 0;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//
//        if (remoteMessage.getData().size() > 0) {
//            String type = remoteMessage.getData().get("NotificationType");
//            String id = remoteMessage.getData().get("ID");
//            String title = remoteMessage.getData().get("Title");
//            String message = remoteMessage.getData().get("Message");
//            String date = remoteMessage.getData().get("DateServer");
//
//            createMyNotification(message, title, type);
//
//        }
//    }
//
//
//    public void createMyNotification(String msg, String titlex, String type) {
//
//
////        Context context = getApplicationContext();
////        Intent notificationIntent = new Intent(context,NotificationActivity.class);
////        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification noti = new Notification.Builder(this)
//                .setTicker(titlex)
//                .setContentTitle(titlex)
//                .setContentText(msg)
//                .setWhen(System.currentTimeMillis())
//                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
//                .setSmallIcon(R.mipmap.ic_launcher).build();
////                .setContentIntent(pIntent).getNotification();
//        noti.flags = Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(index, noti);
//    }
//}
