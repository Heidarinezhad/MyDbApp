package com.khn.mydbapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import dmax.dialog.SpotsDialog;
import com.khn.mydbapp.models.User;
import com.khn.mydbapp.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView tV1,tV2,tV3,tV4, HeaderTitle, HeaderTitleSub;
    private Button btnLogout,btnRegister;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mytoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView imgvw = navView.findViewById(R.id.imageView);
        HeaderTitle = navView.findViewById(R.id.textViewHeader);
        HeaderTitleSub = navView.findViewById(R.id.textViewSub);
        imgvw.setImageResource(R.drawable.logoico);
        navigationView.setNavigationItemSelectedListener(this);
        //--------------------------------//Left side menu ---------------------------------
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mytoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //------------------------Floating Action---------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    //----------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //------------------------// Right Menu List----------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//-----------------------------------------------------------------------------
    @Override
    public void onResume(){
        super.onResume();

        user = SharedPrefManager.getmInstance(this).getUser();

        HeaderTitle.setText(user.getFullname());
        HeaderTitleSub.setText(user.getEmail());

        tV1 = findViewById(R.id.UsernameText);
        tV1.setText("Username : "+user.getUsername());

        tV2 = findViewById(R.id.FullnameText);
        tV2.setText("Fullname : "+user.getFullname());

        tV3 = findViewById(R.id.EmailText);
        tV3.setText("Email : "+user.getEmail());

        tV4 = findViewById(R.id.CreatedAtText);
        tV4.setText("Created At: "+user.getCreatedAt());

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
                Toast.makeText(getApplicationContext(), "You Choose Help",Toast.LENGTH_LONG).show();
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
        Intent i;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        int id = item.getItemId();
        switch(id){
       //--------------------------------General Items
            case R.id.nav_home:
                Snackbar.make(findViewById(android.R.id.content), "You Choose Home", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.nav_aboutUs:
                ShowAndWait("About Us.....", 2000);
                break;

            case R.id.nav_sharing:
                Snackbar.make(findViewById(android.R.id.content), "You Choose Sharing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.nav_website:
                Toast.makeText(this, "You Choose Website",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_feedback:
                Toast.makeText(this, "You Choose Feedback",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_ourProducts:
                Snackbar.make(findViewById(android.R.id.content), "You Choose Our Product", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "You Choose Settings",Toast.LENGTH_LONG).show();
                break;
//-------------------Social Networks------------------------
            case R.id.nav_facebook:
                Toast.makeText(this, "You Choose Facebook",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_telegram:
                Snackbar.make(findViewById(android.R.id.content), "You Choose Telegram", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.nav_instagram:
                Toast.makeText(this, "You Choose Instagram",Toast.LENGTH_LONG).show();
                break;
//-------------------User Account------------------------
            case R.id.nav_updateUserInfo:
                i = new Intent(getApplicationContext(), UpdateActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivity(i);
                break;

            case R.id.nav_newUser:
                i = new Intent(getApplicationContext(), RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                break;

            case R.id.nav_listUser:
                i = new Intent(getApplicationContext(), ShowAllUsersActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                startActivity(i);

                break;

            case R.id.nav_logout:
                 SharedPrefManager.getmInstance(MainActivity.this)
                    .clear();
                 i = new Intent(getApplicationContext(), LoginActivity.class);
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
} //mainclass
