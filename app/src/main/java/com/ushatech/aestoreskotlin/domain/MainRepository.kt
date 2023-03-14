package com.ushatech.aestoreskotlin.domain

import com.ushatech.aestoreskotlin.data.CartDataRemote

class MainRepository {
    suspend fun signUpUserStepOne(fullName:String, mobile:String) = AndroidClient().apiInterface.registerUserStepOne(fullName, mobile)
    suspend fun signUpUserStepTwo(userId: String, otp: String, cartDataRemote: ArrayList<CartDataRemote>) = AndroidClient().apiInterface.registerUserStepTwo(userId, otp,cartDataRemote)

    //Listing all the login api here's in this section

    suspend fun loginUserStepOne(mobileNumber:String) =AndroidClient().apiInterface.loginUserStepOne(mobileNumber)
    suspend fun loginUserStepTwo(
        otp:String,
        userId: String,
        cartDataRemote: ArrayList<CartDataRemote>?
    ) = AndroidClient().apiInterface.loginUserStepTwo(userId, otp,cartDataRemote)

    // Get home screen api is listed here

    suspend fun getHomeScreenData() = AndroidClient().apiInterface.getHomeScreen()

    suspend fun getAllCategories() = AndroidClient().apiInterface.getAllCategories()

    suspend fun getAllSubcategoryForCategory(categoryId:String) = AndroidClient().apiInterface.getAllSubCategoryForCategory(categoryId)
    suspend fun  getAllSuperCategory(subCategoryId: String) = AndroidClient().apiInterface.getAllSuperCateogryByCategory(subCategoryId)
    suspend fun searchProduct(
        searchTerm: String?,
        categoryId: Int?,
        subCategoryId: Int?,
        superCateogryId: Int?,
        page:Int) = AndroidClient().apiInterface.searchProducts(searchTerm, categoryId, subCategoryId,page,superCateogryId)

    suspend fun getCountries() = AndroidClient().apiInterface.getCountries()
    suspend fun getStates(countryId:String) = AndroidClient().apiInterface.getStates(countryId)

    suspend fun getCities(stateId:String) = AndroidClient().apiInterface.getCities(stateId)

    suspend fun getProductDetail(productId:String,userId:String) = AndroidClient().apiInterface.getProductDetail(productId, userId)


    suspend fun checkPincode(productId:String,pincode:String) = AndroidClient().apiInterface.checkPincode(productId, pincode)

    // Cart webservices are placed here.


    suspend fun getUserCart(userId:String) = AndroidClient().apiInterface.getUserCart(userId)

    suspend fun addToCart(userId: String,productId:String,quantity:String) = AndroidClient().apiInterface.addToCart(userId, productId, quantity)

    suspend fun deleteItemCart(userId: String,cartId:String) = AndroidClient().apiInterface.deleteCart(userId, cartId)

    suspend fun deleteCompleteCart(userId: String) = AndroidClient().apiInterface.deletAllItems(userId)


}