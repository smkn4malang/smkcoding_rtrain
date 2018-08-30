package com.example.robet.rtrain;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiInterface {

    @FormUrlEncoded
    @POST("public/index.php/admin/login")
    Call<Value> loginAdmin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("public/index.php/user/login")
    Call<Value> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("public/index.php/user/register")
    Call<Value> registerUser(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("public/index.php/user/forgot")
    Call<Value> forgotUser(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("public/index.php/user/forgot2")
    Call<Value> forgot2User(
            @Field("token") String token,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/forgot")
    Call<Value> forgotAdmin(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/forgot2")
    Call<Value> forgot2Admin(
            @Field("token") String token,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("public/index.php/guest/login")
    Call<Value> loginGuest(
            @Field("name") String name,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("public/index.php/change/name")
    Call<Value> ChangeName(
            @Field("id") int id,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("public/index.php/change/pw")
    Call<Value> ChangePW(
            @Field("id") int id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("public/index.php/credit/add")
    Call<Value> creditAdd(
            @Field("id") int id,
            @Field("credit") String credit,
            @Field("tax") int tax,
            @Field("pay") String pay
    );

    @GET("public/index.php/user/show")
    Call<UserShowResponse> UserShow();

    @FormUrlEncoded
    @POST("public/index.php/user/update")
    Call<Value> UserUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("credit") String credit
    );

    @FormUrlEncoded
    @POST("public/index.php/user/delete")
    Call<Value> UserDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/index.php/user/add")
    Call<Value> UserAdd(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("credit") String credit
    );

    @GET("public/index.php/admin/show")
    Call<AdminResponse> AdminShow();

    @FormUrlEncoded
    @POST("public/index.php/admin/add")
    Call<Value> AdminAdd(
            @Field("name")  String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/update")
    Call<Value> AdminUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/delete")
    Call<Value> AdminDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/change/username")
    Call<Value> AdminChangeUsername(
            @Field("id") int id,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/change/email")
    Call<Value> AdminChangeEmail(
            @Field("id") int id,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/change/password")
    Call<Value> AdminChangePassword(
            @Field("id") int id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("public/index.php/admin/change/name")
    Call<Value> AdminChangeName(
            @Field("id") int id,
            @Field("name") String name
    );

    @GET("public/index.php/item/show")
    Call<ItemResponse> ItemShow();

    @FormUrlEncoded
    @POST("public/index.php/item/update")
    Call<Value> ItemUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("price") String price,
            @Field("desc") String desc
    );

    @FormUrlEncoded
    @POST("public/index.php/item/delete")
    Call<Value> ItemDelete(
            @Field("id") String id
    );

    @GET("public/index.php/city/show")
    Call<CityResponse> CityList();

    @FormUrlEncoded
    @POST("public/index.php/seat/show")
    Call<SeatResponse> SeatList(
            @Field("trainId") String trainId,
            @Field("date") String date,
            @Field("time") String time,
            @Field("category") String category,
            @Field("destination") String destination,
            @Field("depart") String depart
    );

    @GET("public/index.php/train/show")
    Call<TrainResponse> trainShow();

    @FormUrlEncoded
    @POST("public/index.php/train/add")
    Call<Value> TrainAdd(
            @Field("name") String name,
            @Field("category") String category,
            @Field("price") String price,
            @Field("cars") String cars
    );

    @FormUrlEncoded
    @POST("public/index.php/train/delete")
    Call<Value> TrainDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("public/index.php/train/update")
    Call<Value> TrainUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("category") String category,
            @Field("price") String price,
            @Field("cars") String cars
    );

    @FormUrlEncoded
    @POST("public/index.php/train/search")
    Call<TrainResponse> TrainSearch(
            @Field("date") String date,
            @Field("category") String category
    );

    @GET("public/index.php/time/show")
    Call<CityResponse> TimeShow();
}
