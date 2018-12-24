package com.khn.mydbapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khn.mydbapp.R;
import com.khn.mydbapp.api.Api;
import com.khn.mydbapp.api.RetrofitClient;
import com.khn.mydbapp.api.UsersAdapter;
import com.khn.mydbapp.models.User;
import com.khn.mydbapp.models.UserListResponse;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllUsersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<UserListResponse> userListResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);
        recyclerView = findViewById(R.id.recyclerView);
        getUserListData(); // call a method in which we have implement our GET type web API
    }
    //----------------------------------------------------------------
    private void getUserListData() {

        ShowAndWait("Please Wait.....", 1000);

        Call<List<UserListResponse>> call = RetrofitClient.getmInstance()
                .getApi().getUsersList();

        call.enqueue(new Callback<List<UserListResponse>>() {
            @Override
            public void onResponse(Call<List<UserListResponse>> call, Response<List<UserListResponse>> response) {
                userListResponseData = response.body();
                setDataInRecyclerView();
            }

            @Override
            public void onFailure(Call<List<UserListResponse>> call, Throwable t) {
                ShowAndWait(t.toString(), 10000);

            }
         });
    }
//----------------------------------------------------------
    private void setDataInRecyclerView() {
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        UsersAdapter usersAdapter = new UsersAdapter(this, userListResponseData);

        recyclerView.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }
    //----------------------------------------------------------------------------------------------------------
    public void ShowAndWait(String msg, int timeout){
        final AlertDialog alertDialog= new  SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }},timeout);
    }
//-----------------------------------------
}