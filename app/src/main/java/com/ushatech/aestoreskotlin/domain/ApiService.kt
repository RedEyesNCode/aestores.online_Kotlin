package com.ushatech.aestoreskotlin.domain

import com.ushatech.aestoreskotlin.data.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("api/v1/subCategoriesByCategoryId")
    fun getAllSubCategoryForCategory(@Query("categoryId") categoryId:String):Call<SubCateogryResponse>


    @GET("api/v1/superCategoriesBySubcategoryId")
    fun getAllSuperCateogryByCategory(@Query("subCategoryId") subCategoryId:String):Call<SubCateogryResponse>


    @GET("api/v1/products")
    fun searchProducts(@Query ("search") searchTerm:String, @Query("categoryId") categoryId: Int?, @Query("subCategoryId") subCategoryId: Int?, @Query("page") page: Int?, @Query("superCategoryId") superCategoryId: Int?):Call<SearchProductResponse>

    // Product detail api

    @GET("api/v1/product")
    fun getProductDetail(@Query("productId") productId:String,@Query("userid") userId: String):Call<ProductDetailResponseData>


    // For Country, state and City

    @GET("api/v1/countries")
    fun getCountries():Call<SelectionResponseData>

    @GET("api/v1/states")
    fun getStates(@Query("countryId") countryId:String):Call<SelectionResponseData>

    @GET("api/v1/cities")
    fun getCities(@Query("stateId") stateId:String):Call<SelectionResponseData>
}


