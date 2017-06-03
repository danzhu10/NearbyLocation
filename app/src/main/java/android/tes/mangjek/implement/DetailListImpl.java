package android.tes.mangjek.implement;

import android.tes.mangjek.model.PlaceDetails;
import android.tes.mangjek.network.ApiClient;
import android.tes.mangjek.network.ApiService;
import android.tes.mangjek.presenter.CallBackView;
import android.tes.mangjek.presenter.DetailListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class DetailListImpl implements DetailListener {

    ApiService service;
    CallBackView callBackView;

    public DetailListImpl(CallBackView callBackView) {
        this.callBackView = callBackView;
        service = ApiClient.service;
    }

    @Override
    public void onGetDetail(String key, String reference) {
        service.getDetails(key, reference, "false").enqueue(new Callback<PlaceDetails>() {
            @Override
            public void onResponse(Call<PlaceDetails> call, Response<PlaceDetails> response) {
                callBackView.onCallSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PlaceDetails> call, Throwable t) {
                callBackView.onCallError(t);
            }
        });

    }


}
