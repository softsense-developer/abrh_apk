package com.example.takepicture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

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
    public String encoded ="";
 //   private List<Datum> dat;

   // String sBaseUrl="";
   // One Button
   Button BSelectImage;

    // One Preview Image
 //   ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mimageView=findViewById(R.id.imageView);
        btSubmit=findViewById(R.id.bt_submit);
        tvResult=findViewById(R.id.tvResult);

      //  BSelectImage = findViewById(R.id.BSelectImage);
    //    IVPreviewImage = findViewById(R.id.IVPreviewImage);

    //    BSelectImage.setOnClickListener(new View.OnClickListener() {
      //      @Override
        //    public void onClick(View v) {
          //      imageChooser();
           // }
       // });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override //get request
           
                //post request
            public void onClick(View v) {
                ApiInterface methods = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                PhotoSendReq req = new PhotoSendReq();
                req.setImage(encoded.toString());
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
                     //   encoded="";
                       // ArrayList<Model>
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage() );
                       // encoded="";
                    }
                });

            }
        });


    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void takePicture(View view) {
        encoded="";
      CropImage.activity()
              .setCropMenuCropButtonTitle("Kırp")
              .setGuidelines(CropImageView.Guidelines.ON)
              .start(this);

        tvResult.setText(" ");
        //Intent imageTakeIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if(imageTakeIntent.resolveActivity(getPackageManager())!= null){
        ///  startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mimageView.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
             encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.e(TAG, "base64 : "+encoded );

        }
        if (requestCode == SELECT_PICTURE) {
            // Get the url of the image from data
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
               // IVPreviewImage.setImageURI(selectedImageUri);
                mimageView.setImageURI(selectedImageUri);

                Uri imageUri = Objects.requireNonNull(data).getData();
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    byte[] imageBytes = imageToByteArray(bitmap);
                    encoded = Base64.encodeToString(imageBytes, Base64.DEFAULT); // actual conversion to Base64 String Image
                    // display the Base64 String Image encoded text
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                if (null != resultUri) {
                    mimageView.setImageURI(resultUri);

                    //Uri imageUri = Objects.requireNonNull(data).getData();
                    Bitmap bitmap = null;

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        byte[] imageBytes = imageToByteArray(bitmap);
                        encoded = Base64.encodeToString(imageBytes, Base64.DEFAULT); // actual conversion to Base64 String Image
                        // display the Base64 String Image encoded text
                    } catch (IOException e) {
                        Log.e("err",e.getMessage());
                        e.printStackTrace();
                    }
                }else{
                    Log.e("image",null);
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(TAG, "image cropper : "+ error);

            }
        }

    }
    private byte[] imageToByteArray(Bitmap bitmapImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        return baos.toByteArray();
    }
    public interface APIService {
        @GET("api/auth/verify/")
        Call<VeriListem> verilerimilistele();
    }
    public void sendPicture(View view) {

        btSubmit=findViewById(R.id.bt_submit);

    }
}