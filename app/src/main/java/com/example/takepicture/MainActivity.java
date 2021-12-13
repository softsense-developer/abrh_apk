package com.example.takepicture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView mimageView;
    private static final int REQUEST_IMAGE_CAPTURE=101;
    private Button btSubmit;
    private TextView tvResult;
 //   private List<Datum> dat;

    String sBaseUrl="http://abrh.insense.com.tr/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mimageView=findViewById(R.id.imageView);
        btSubmit=findViewById(R.id.bt_submit);
        tvResult=findViewById(R.id.tvResult);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override //get request
            /*public void onClick(View v) {
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Model> call =methods.getAllData();
                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Log.e(TAG,"onResponse: code"+response.code());
                        Log.e(TAG, "onResponse: "+response.body().getMessage() );


                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage() );
                    }
                });*/
                //post request
            public void onClick(View v) {
                ApiInterface methods = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                PhotoSendReq req = new PhotoSendReq();
                req.setImage("test");
                Call<Model> call =methods.getBloodData(req);
                Log.e(TAG, "onClick: "+call.toString() );
                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                     if(response.body() != null){
                         Log.e(TAG,"onResponse: code "+response.code());
                         Log.e(TAG, "onResponse: code "+response.body().getMessage() );
                         //  Log.e(TAG, "onResponse:message "+response.body().getMessage() );
                         //  Log.e(TAG, "onResponse: errors "+response.body().getErrors() );
                         tvResult.setText(response.body().getMessage());
                     }
                       // ArrayList<Model>
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage() );
                    }
                });

            }
        });


    }

    public void takePicture(View view) {

        Intent imageTakeIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mimageView.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.e(TAG, "base64 : "+encoded );

        }


    }

    public interface APIService {
        @GET("api/auth/verify/")
        Call<VeriListem> verilerimilistele();
    }
    public void sendPicture(View view) {

        btSubmit=findViewById(R.id.bt_submit);

    }
}