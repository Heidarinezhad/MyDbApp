package com.khn.mydbapp.api;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.120/MyDbApp/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private static OkHttpClient httpClient =
            new OkHttpClient.Builder().connectTimeout(5,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

    private RetrofitClient() {
       retrofit = new Retrofit.Builder()
                 .client(httpClient)
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
        }

        public static synchronized RetrofitClient getmInstance(){
         if (mInstance ==null)
             mInstance = new RetrofitClient();
         return mInstance;
        }

        public Api getApi(){
          return retrofit.create(Api.class);
        }
    }

