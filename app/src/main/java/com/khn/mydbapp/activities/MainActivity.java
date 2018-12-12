package com.khn.mydbapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.khn.mydbapp.R;
import com.khn.mydbapp.models.User;
import com.khn.mydbapp.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private TextView tV1,tV2,tV3,tV4;
    private Button btnLogout,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = SharedPrefManager.getmInstance(this).getUser();

        tV1 = findViewById(R.id.UsernameText);
        tV1.setText("Username : "+user.getUsername());

        tV2 = findViewById(R.id.FullnameText);
        tV2.setText("Fullname : "+user.getFullname());

        tV3 = findViewById(R.id.EmailText);
        tV3.setText("Email : "+user.getEmail());

        tV4 = findViewById(R.id.CreatedAtText);
        tV4.setText("Created At: "+user.getCreatedAt());
//-----------------------------------------------------
        btnLogout = findViewById(R.id.Logoutbutton);
        btnLogout.setText("Logout");
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getmInstance(MainActivity.this)
                        .clear();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
//-----------------------------------------------------
        btnRegister = findViewById(R.id.Registerbutton);
        btnRegister.setText("Register");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getmInstance(MainActivity.this)
                        .clear();
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
//-----------------------------------------------------------------


    } //oncreate


    //--------------------------------------------------------
} //mainclass
