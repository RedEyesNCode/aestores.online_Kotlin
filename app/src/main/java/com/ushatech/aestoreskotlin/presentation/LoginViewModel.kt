package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.LoginUserResponse
import com.ushatech.aestoreskotlin.data.OtpSendResponse
import com.ushatech.aestoreskotlin.data.RegisterUserStepTwoResponse
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginViewModel():ViewModel() {
    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _otpSendResponse = MutableLiveData<OtpSendResponse>()
    val otpSendResponse :LiveData<OtpSendResponse> = _otpSendResponse

    private val _loginUserResponse = MutableLiveData<LoginUserResponse> ()
    val loginUserResponse:LiveData<LoginUserResponse> = _loginUserResponse

    val mainRepo = MainRepository()




    fun loginUserStepTwo(userId: String,otp:String) = viewModelScope.launch {

        loginUserStepTwoCoroutine(userId,otp)





    }

    private suspend fun loginUserStepTwoCoroutine(userId: String, otp: String) {
        try {

            val response = mainRepo.loginUserStepTwo(otp, userId)
            _isLoading.value = true
            response.enqueue(object : Callback<LoginUserResponse> {

                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _loginUserResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
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

    fun loginUserStepOne(mobileNumber:String) = viewModelScope.launch {
        loginUserStepOneCoroutine(mobileNumber)


    }

    private suspend fun loginUserStepOneCoroutine(mobileNumber: String) {
        // To be implemented api call.

        try {

            val response = mainRepo.loginUserStepOne(mobileNumber)
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