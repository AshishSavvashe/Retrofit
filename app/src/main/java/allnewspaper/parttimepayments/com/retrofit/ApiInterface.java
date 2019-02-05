package allnewspaper.parttimepayments.com.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("android/jsonandroid")
    Call<Example> getAndroidVersion(@Field("Content-Type") String type);
}
