package com.ushatech.aestoreskotlin.domain

class MainRepository {
    suspend fun signUpUserStepOne(fullName:String, mobile:String) = AndroidClient().apiInterface.registerUserStepOne(fullName, mobile)
    suspend fun signUpUserStepTwo(userId:String,otp:String) = AndroidClient().apiInterface.registerUserStepTwo(userId, otp)

    //Listing all the login api here's in this section

    suspend fun loginUserStepOne(mobileNumber:String) =AndroidClient().apiInterface.loginUserStepOne(mobileNumber)
    suspend fun loginUserStepTwo(otp:String,userId: String) = AndroidClient().apiInterface.loginUserStepTwo(userId, otp)

    // Get home screen api is listed here

    suspend fun getHomeScreenData() = AndroidClient().apiInterface.getHomeScreen()

    suspend fun getAllCategories() = AndroidClient().apiInterface.getAllCategories()

    suspend fun searchProduct(
        searchTerm:String,
        categoryId: Int?,
        subCategoryId: Int?,
        superCateogryId: Int?,
        page:Int) = AndroidClient().apiInterface.searchProducts(searchTerm, categoryId, subCategoryId,page,superCateogryId)

}