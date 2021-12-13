package com.example.takepicture;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @Headers({"Content-Type: application/json", "charset=utf-8","content-length: 44","access-control-allow-origin: * ","accept: text/plain","Accept-Path: true","Cache-Control: max-age=640000"})
    @POST("api/auth/register")
    Call<Model> getBloodData(@Field("image")String image);

}
