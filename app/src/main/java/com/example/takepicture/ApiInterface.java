package com.example.takepicture;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("api/auth/register")
    Call<Model> getBloodData(@Body PhotoSendReq req);

}
