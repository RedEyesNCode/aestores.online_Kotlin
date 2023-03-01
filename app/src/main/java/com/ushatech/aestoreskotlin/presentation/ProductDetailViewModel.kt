package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.CommonResponseData
import com.ushatech.aestoreskotlin.data.ProductDetailResponseData
import com.ushatech.aestoreskotlin.data.SelectionResponseData
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductDetailViewModel(): ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading
    val mainRepository = MainRepository()


    private val _productDetailResponse = MutableLiveData<ProductDetailResponseData>()
    val productResponse :LiveData<ProductDetailResponseData> = _productDetailResponse

    private val _commonResponse = MutableLiveData<CommonResponseData>()
    val commonResponseData :LiveData<CommonResponseData> = _commonResponse

    fun checkPincode(productId: String,pincode:String) = viewModelScope.launch {

        checkPincodeCoroutine(productId,pincode)

    }

    private suspend fun checkPincodeCoroutine(productId: String, pincode: String) {
        try {

            val response = mainRepository.checkPincode(productId, pincode)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseData> {

                override fun onResponse(
                    call: Call<CommonResponseData>,
                    response: Response<CommonResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _commonResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<CommonResponseData>, t: Throwable) {
                    _isFailed.value = t.message
                }
            })
        } catch (t: Throwable) {

            when (t) {
                is IOException -> {
                    _isFailed.value = "IO Exception"
                }
                else -> {
                    _isFailed.value = "Exception." + t.message

                    Log.i("RETROFIT", t.message!!)
                }


            }

        }




    }


    fun getProductDetail(productId:String,userId:String) = viewModelScope.launch {

        getProductDetailCoroutine(productId,userId)


    }

    private suspend fun getProductDetailCoroutine(productId: String, userId: String) {
        try {

            val response = mainRepository.getProductDetail(productId, userId)
            _isLoading.value = true
            response.enqueue(object : Callback<ProductDetailResponseData> {

                override fun onResponse(
                    call: Call<ProductDetailResponseData>,
                    response: Response<ProductDetailResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _productDetailResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<ProductDetailResponseData>, t: Throwable) {
                    _isFailed.value = t.message
                }
            })
        } catch (t: Throwable) {

            when (t) {
                is IOException -> {
                    _isFailed.value = "IO Exception"
                }
                else -> {
                    _isFailed.value = "Exception." + t.message

                    Log.i("RETROFIT", t.message!!)
                }


            }

        }

    }


}