package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.CartDataRemote
import com.ushatech.aestoreskotlin.data.OtpSendResponse
import com.ushatech.aestoreskotlin.data.RegisterUserStepTwoResponse
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SignupViewModel():ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _otpSendResponse = MutableLiveData<OtpSendResponse>()
    val otpSendResponse :LiveData<OtpSendResponse> = _otpSendResponse

    private val _signupResponse = MutableLiveData<RegisterUserStepTwoResponse>()
    val signupResponse :LiveData<RegisterUserStepTwoResponse> = _signupResponse




    var mainRepo: MainRepository = MainRepository()


    fun registerUserStepTwo(userId:String, otp:String, cartDataRemote: ArrayList<CartDataRemote>?) = viewModelScope.launch {

        if (cartDataRemote != null) {
            registerUserStepTwoCoroutine(userId, otp,cartDataRemote)

        }else{
            val cartDataRemoteTemp = ArrayList<CartDataRemote>()
            registerUserStepTwoCoroutine(userId, otp,cartDataRemoteTemp)

        }
    }

    private suspend fun registerUserStepTwoCoroutine(
        userId: String, otp: String,
        cartDataRemote: ArrayList<CartDataRemote>
    ) {

        try {

            val response = mainRepo.signUpUserStepTwo(userId, otp,cartDataRemote)
            _isLoading.value = true
            response.enqueue(object : Callback<RegisterUserStepTwoResponse> {

                override fun onResponse(
                    call: Call<RegisterUserStepTwoResponse>,
                    response: Response<RegisterUserStepTwoResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _signupResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<RegisterUserStepTwoResponse>, t: Throwable) {
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


    fun registerUserStepOne(fullName:String,mobile:String)= viewModelScope.launch {

        registerUserStepOneCoroutine(fullName,mobile)


    }

    private suspend fun registerUserStepOneCoroutine(fullName: String, mobile: String) {
        try {

            val response = mainRepo.signUpUserStepOne(fullName, mobile)
            _isLoading.value = true
            response.enqueue(object : Callback<OtpSendResponse> {

                override fun onResponse(
                    call: Call<OtpSendResponse>,
                    response: Response<OtpSendResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _otpSendResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<OtpSendResponse>, t: Throwable) {
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