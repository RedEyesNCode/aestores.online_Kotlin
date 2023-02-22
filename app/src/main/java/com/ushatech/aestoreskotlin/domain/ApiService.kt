package com.ushatech.aestoreskotlin.domain

import com.ushatech.aestoreskotlin.data.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/register")
    fun registerUserStepOne(@Field("name") fullName:String, @Field("mobile") mobile:String):Call<OtpSendResponse>


    @FormUrlEncoded
    @POST("api/v1/register")
    fun registerUserStepTwo(@Field("userid") userId:String, @Field("otp") otp:String):Call<RegisterUserStepTwoResponse>


    @FormUrlEncoded
    @POST("api/v1/login")
    fun loginUserStepOne(@Field("mobile") mobileNumber:String):Call<OtpSendResponse>

    @FormUrlEncoded
    @POST("api/v1/login")
    fun loginUserStepTwo(@Field("userid") userId: String,@Field("otp") otp:String):Call<LoginUserResponse>


    @GET("api/v1/homescreen")
    fun getHomeScreen():Call<HomeScreenResponse>


    @GET("/api/v1/allCategories")
    fun getAllCategories() :Call<AllCategoryResponse>


}


