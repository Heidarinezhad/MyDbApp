package com.khn.mydbapp.api;

import com.khn.mydbapp.models.DefaultResponse;
import com.khn.mydbapp.models.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {
    @FormUrlEncoded
    @POST("createuser.php")
    Call<ResponseBody> createUser(
                         @Field("username") String username,
                         @Field("email") String email,
                         @Field("fullname") String fullname,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> userLogin(
                        @Field("username") String username,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("update.php")
    Call<DefaultResponse> userUpdate(
            @Field("username") String username,
            @Field("email") String email,
            @Field("fullname") String fullname,
            @Field("password") String password);
}

