package android.tes.mangjek.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by Alfarobi on 29/12/2015.
 */
public class Utils {

    public static Toast toastLong(Context context,String s){
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        toast.show();
        return toast;
    }

    public static Toast toastShort(Context context,String s){
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }


    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}
