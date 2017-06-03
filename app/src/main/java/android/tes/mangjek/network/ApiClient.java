package android.tes.mangjek.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    private ApiClient() {}
    static OkHttpClient okHttpClient;
    static HttpLoggingInterceptor httpLoggingInterceptor;
    private static String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    public static final OkHttpClient okHttpClients() {
        httpLoggingInterceptor =new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
        return okHttpClient;
    }

    public static final ApiService service = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClients())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

}
