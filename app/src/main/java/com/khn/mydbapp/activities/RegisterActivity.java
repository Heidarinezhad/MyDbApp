package com.khn.mydbapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button btnLogin,btnRegister;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

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
    //-----------------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
       // if(SharedPrefManager.getmInstance(this).isNotLoggedIn()){
       //     Intent intent = new Intent(this, MainActivity.class);
       //     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       //     startActivity(intent);
       //     overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
       // }
    }
    //--------------------------------------------------------
    private void registerUser() {
        ShowAndWait("Registering New User.....", 1000);

        Call<ResponseBody> call = RetrofitClient
            .getmInstance()
            .getApi()
            .createUser(username,email,fullname,password);

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
