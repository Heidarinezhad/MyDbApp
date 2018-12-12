package com.khn.mydbapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsername, etLoginPassword;
    private String username,password;
    private Button btnLogin,btnRegister;
    private ProgressDialog pDialog;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        User u = new User("","","","");
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etLoginUsername.getText().toString().toLowerCase().trim();
                password = etLoginPassword.getText().toString().trim();

                 if(validateInputs()) {
                     userLogin();
                 }
            }
        });
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
              //  overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
    }

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
    private void userLogin(){

        displayLoader();
        final User u = new User("","","","");
        Call<LoginResponse> call = RetrofitClient
                .getmInstance().getApi().userLogin(username,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                if (loginResponse.getStatus() == 0) {  //Successful login
                    u.setUsername(username);
                    u.setFullname(loginResponse.getFullname());
                    u.setEmail(loginResponse.getEmail());
                    u.setCreatedAt(loginResponse.getCreatedAt());

                    SharedPrefManager.getmInstance(LoginActivity.this)
                    .saveUser(u);

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                    }
                    else
                       Toast.makeText(LoginActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
    //----------------------------------------------------
    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}