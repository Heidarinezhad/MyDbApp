package com.khn.mydbapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khn.mydbapp.R;
import com.khn.mydbapp.models.User;
import com.khn.mydbapp.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView tV1,tV2,tV3,tV4;
    private Button btnLogout,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mytoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);

        User user = SharedPrefManager.getmInstance(this).getUser();

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Welcome");
        //getSupportActionBar().setSubtitle("User : " + user.getFullName());
        getSupportActionBar().setSubtitle("User : ");

        //R.string.nav_header_subtitle= user.getFullName();

        //------------------------Floating Action---------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //------------------------------------------------------
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mytoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        // added by me
        View navView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView imgvw = (ImageView)navView.findViewById(R.id.imageView);
     //   TextView tv = (TextView)navView.findViewById(R.id.textView);
        imgvw.setImageResource(R.drawable.logo1);
       // tv.setText(user.getFullName());
        //------
        navigationView.setNavigationItemSelectedListener(this);
    }
    //----------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //----------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //----------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "You Choose Action Setting",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_help:
                Toast.makeText(this, "You Choose Help",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //----------------------------------------------------------
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        int id = item.getItemId();
        switch(id){
            case R.id.nav_camera:
                Toast.makeText(this, "You Choose Camera",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_gallery:
                Toast.makeText(this, "You Choose Gallery",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_slideshow:
                Toast.makeText(this, "You Choose Slideshow",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_tools:
                Toast.makeText(this, "You Choose Tools",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_changePass:
                Toast.makeText(this, "You Choose Change Password",Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_logout:
                 SharedPrefManager.getmInstance(MainActivity.this)
                    .clear();
                 Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(i);
                 overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                 finish();
                 break;

            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    } //oncreate


    //--------------------------------------------------------
} //mainclass
