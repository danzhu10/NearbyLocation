package android.tes.mangjek.presenter;

/**
 * Created by EduSPOT on 09/12/2016.
 */

public interface CallBackView {
    void onCallError(Throwable t);
    void onCallSuccess(Object object);
}
