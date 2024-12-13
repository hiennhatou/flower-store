package edu.ou.flowerstore.utils.zalopay;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ZaloPayApi {
    @POST("/v2/create")
    Call<ResponseCreateZalopayOrderBody> createOrder(@Body RequestCreateZalopayOrderBody body);
}
