package com.example.robet.rtrain.support;

import com.example.robet.rtrain.apiInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    private static apiInterface service;

    public static apiInterface getData() {
        if (service == null) {
            String API_BASE_URL = "http://nothing-aframrpy.000webhostapp.com/rtrain/";
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit =
                    builder.client(httpClient.build()).build();
            service = retrofit.create(apiInterface.class);
        }
        return service;
    }
}
