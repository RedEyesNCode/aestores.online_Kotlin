package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.CartUserResponse
import com.ushatech.aestoreskotlin.data.CommonResponseData
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CartViewModel():ViewModel() {


    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading


    val mainRepository = MainRepository()
    private val _commonResponseData = MutableLiveData<CommonResponseData>()
    val commonResponseData :LiveData<CommonResponseData> = _commonResponseData
    private val _deleteItemResponse = MutableLiveData<CommonResponseData>()
    val deleteItemResponse :LiveData<CommonResponseData> = _deleteItemResponse


    private val _updateItemResponse = MutableLiveData<CommonResponseData>()
    val updateItemResponse:LiveData<CommonResponseData> = _updateItemResponse


    private val _deleteCompleteItemResponse = MutableLiveData<CommonResponseData>()
    val deleteCompleteItemResponse :LiveData<CommonResponseData> = _deleteCompleteItemResponse


    private val _userCartResponse = MutableLiveData<CartUserResponse>()
    val userCartUserResponse :LiveData<CartUserResponse> = _userCartResponse



    fun updateUserCart(userId: String,cartId: String,quantity:String) = viewModelScope.launch {

        updateUserCartCoroutine(userId,cartId,quantity)


    }

    private suspend fun updateUserCartCoroutine(userId: String, cartId: String, quantity: String) {

        try {
            val response = mainRepository.updateCart(userId,cartId,quantity)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseData> {

                override fun onResponse(
                    call: Call<CommonResponseData>,
                    response: Response<CommonResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _updateItemResponse.postValue(response.body())
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

    fun getUserCart(userId:String) = viewModelScope.launch {
        getUserCartCoroutine(userId)

    }
    fun deleteEntireCart(userId: String) = viewModelScope.launch {

        deleteEntireCouroutine(userId)

    }

    private suspend fun deleteEntireCouroutine(userId: String) {
        try {
            val response = mainRepository.deleteCompleteCart(userId)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseData> {

                override fun onResponse(
                    call: Call<CommonResponseData>,
                    response: Response<CommonResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _deleteCompleteItemResponse.postValue(response.body())
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

    fun deleteCartItem(userId:String,cartId:String) = viewModelScope.launch {
        deleteCartItemCoroutine(userId,cartId)


    }

    private suspend fun deleteCartItemCoroutine(userId: String, cartId: String) {
        try {
            val response = mainRepository.deleteItemCart(userId,cartId)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseData> {

                override fun onResponse(
                    call: Call<CommonResponseData>,
                    response: Response<CommonResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _deleteItemResponse.postValue(response.body())
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


    private suspend fun getUserCartCoroutine(userId: String) {
        try {
            val response = mainRepository.getUserCart(userId)
            _isLoading.value = true
            response.enqueue(object : Callback<CartUserResponse> {

                override fun onResponse(
                    call: Call<CartUserResponse>,
                    response: Response<CartUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _userCartResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<CartUserResponse>, t: Throwable) {
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