package android.tes.mangjek.network;

import android.tes.mangjek.model.PlaceDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by EduSPOT on 23/04/2017.
 */

public interface ApiService {

    @GET("details/json")
    Call<PlaceDetails> getDetails(@Query("key") String key, @Query("reference") String refe, @Query("sensor")String sensor);
}
