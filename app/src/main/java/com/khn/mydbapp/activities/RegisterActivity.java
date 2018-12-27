package com.khn.mydbapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.khn.mydbapp.R;
import com.khn.mydbapp.api.RetrofitClient;
import com.khn.mydbapp.storage.SharedPrefManager;

import java.io.IOException;
import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private String   username,   fullname,   email,   password,   confirmPassword;
    private EditText etUsername, etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnLogin,btnRegister, btnChoose;
    private ImageView imageView;
    private Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        imageView = findViewById(R.id.imageView);


        //---------------------------Image Choose Click-------------------------------------------
        btnChoose = findViewById(R.id.btnChooseImage);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChoose();
            }
        });
       //----------------------------Login Click-------------------------------------------
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });

        //--------------------------Register Click---------------------------------------------
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString().toLowerCase().trim();
                fullname = etFullName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                email= etEmail.getText().toString().trim().toLowerCase();
                //displayLoader();
                if (validateInputs())
                    registerUser();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            byte[] byteArray = data.getByteArrayExtra("image");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bmp);
        }
    }

//--------------------------------------------------------
    private void ImageChoose() {
    Intent intent = new Intent(this, GetImageActivity.class);
    startActivityForResult(intent, 1);
}
//--------------------------------------------------------
    private void registerUser() {
        ShowAndWait("Registering New User.....", 1000);

        Call<ResponseBody> call = RetrofitClient
            .getmInstance()
            .getApi()
            .createUser(username,email,fullname,password, password);   //TODO

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String s=response.body().string();
                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ShowAndWait("Server Unreachable!...Check Status or Network Connection", 4000);
            }
        });
        //pDialog.dismiss();
    }
//--------------------------------------------------------
    private boolean validateInputs() {
    if (fullname.isEmpty()) {
        etFullName.setError("Full Name cannot be empty");
        etFullName.requestFocus();
        return false;

    }
    if (username.isEmpty()) {
        etUsername.setError("Username cannot be empty");
        etUsername.requestFocus();
        return false;
    }
    if (email.isEmpty()) {
        etEmail.setError("Email cannot be empty");
        etEmail.requestFocus();
        return false;
    }
    if (password.isEmpty()) {
        etPassword.setError("Password cannot be empty");
        etPassword.requestFocus();
        return false;
    }

    if (confirmPassword.isEmpty()) {
        etConfirmPassword.setError("Confirm Password cannot be empty");
        etConfirmPassword.requestFocus();
        return false;
    }
    if (!password.equals(confirmPassword)) {
        etConfirmPassword.setError("Password and Confirm Password does not match");
        etConfirmPassword.requestFocus();
        return false;
    }

    return true;
}
//---------------------------------------------------------
    public void ShowAndWait(String msg, int timeout){
    final AlertDialog alertDialog= new  SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
    // final AlertDialog alertDialog= new  SpotsDialog.Builder().setContext(this).build();
    alertDialog.setCancelable(false);
    alertDialog.setMessage(msg);
    alertDialog.show();
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            alertDialog.dismiss();
        }},timeout);
}
//--------------------------------------------------------

}
