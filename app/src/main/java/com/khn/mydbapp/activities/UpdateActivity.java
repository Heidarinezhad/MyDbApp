package com.khn.mydbapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khn.mydbapp.R;
import com.khn.mydbapp.api.RetrofitClient;
import com.khn.mydbapp.models.DefaultResponse;
import com.khn.mydbapp.models.User;
import com.khn.mydbapp.storage.SharedPrefManager;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private String   username,   fullname,   email,   password,   confirmPassword, createdAt;
    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private TextView textViewUsername;
    private Button btnUpdate;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        user = SharedPrefManager.getmInstance(this).getUser();

        textViewUsername = findViewById(R.id.textViewUsername);
        username = user.getUsername();
        textViewUsername.setText(username);

        etFullName = findViewById(R.id.etFullName);
        fullname= user.getFullname();
        etFullName.setText(fullname);

        etEmail = findViewById(R.id.etEmail);
        email= user.getEmail();
        etEmail.setText(email);

        createdAt=user.getCreatedAt();

//----------------------------Login Click-------------------------------------------
    btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            fullname = etFullName.getText().toString().trim();
            email = etEmail.getText().toString().trim().toLowerCase();
            password = etPassword.getText().toString().trim();
            confirmPassword = etConfirmPassword.getText().toString().trim();
            createdAt=user.getCreatedAt();

            if (validateInputs())
                UpdateUser();
        }
    });

}
    //--------------------------------------------------------
    private void UpdateUser() {

        ShowAndWait("Updating User...Please Wait!!", 1000);

        final User u = new User(username,email,fullname,createdAt);

        Call<DefaultResponse> call = RetrofitClient
                .getmInstance().getApi().userUpdate(username,email,fullname,password);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse.getStatus() == 0) {
                    u.setUsername(username);
                    u.setFullname(fullname);
                    u.setEmail(email);
                    u.setCreatedAt(createdAt);
                    SharedPrefManager.getmInstance(UpdateActivity.this).saveUser(u);

                    ShowAndWait(defaultResponse.getMsg(), 2000);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                ShowAndWait(t.getMessage(), 2000);
             }
        });

    }
    //--------------------------------------------------------
    private boolean validateInputs() {

        if (fullname.isEmpty())
            fullname=user.getFullname();
        if (email.isEmpty())
            email=user.getEmail();

        if (password.isEmpty()) {
            etPassword.setError("New Password cannot be empty");
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
}

