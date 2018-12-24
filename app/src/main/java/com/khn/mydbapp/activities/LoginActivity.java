package com.khn.mydbapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.khn.mydbapp.R;
import com.khn.mydbapp.api.RetrofitClient;
import com.khn.mydbapp.models.LoginResponse;
import com.khn.mydbapp.models.User;
import com.khn.mydbapp.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsername, etLoginPassword;
    private String username,password;
    private Button btnLogin,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        //User u = new User("","","","");
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                username = etLoginUsername.getText().toString().toLowerCase().trim();
                password = etLoginPassword.getText().toString().trim();

                if(validateInputs())
                        userLogin();
                }
            });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
    }
//---------------------------Oncreate-------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getmInstance(this).isNotLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    //--------------------------------------------------------
    private void userLogin() {

        ShowAndWait("Registering New User.....", 1000);
        final User u = new User("","","","");
        Call<LoginResponse> call = RetrofitClient
                .getmInstance().getApi().userLogin(username,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                Toast.makeText(LoginActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                if (loginResponse.getStatus() == 0) {  //Successful login
                       u.setUsername(username);
                       u.setFullname(loginResponse.getFullname());
                       u.setEmail(loginResponse.getEmail());
                       u.setCreatedAt(loginResponse.getCreatedAt());

                       SharedPrefManager.getmInstance(LoginActivity.this).saveUser(u);

                       Intent i = new Intent(getApplicationContext(), MainActivity.class);
                       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(i);

                        }
                    else
                        ShowAndWait(loginResponse.getMsg(), 1000);

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ShowAndWait("Server Unreachable!...Check Status or Network Connection", 3000);
            }
        });
    }
    //--------------------------------------------------------
    private boolean validateInputs(){
        if(username.isEmpty()){
            etLoginUsername.setError("Username cannot be empty");
            etLoginUsername.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
            return false;
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------------------
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
}