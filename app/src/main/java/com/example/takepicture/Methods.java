package com.example.takepicture;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {
    @GET("api/auth/verify/getTest")
    Call<Model> getAllData();

}
