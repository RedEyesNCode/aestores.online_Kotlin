package com.ushatech.aestoreskotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.LoginUserResponse
import com.ushatech.aestoreskotlin.data.OtpSendResponse
import kotlinx.coroutines.launch

class LoginViewModel():ViewModel() {
    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _otpSendResponse = MutableLiveData<OtpSendResponse>()
    val otpSendResponse :LiveData<OtpSendResponse> = _otpSendResponse

    private val _loginUserResponse = MutableLiveData<LoginUserResponse> ()
    val loginUserResponse:LiveData<LoginUserResponse> = _loginUserResponse


    fun loginUserStepOne(mobileNumber:String) = viewModelScope.launch {
        loginUserStepOneCoroutine(mobileNumber)


    }

    private suspend fun loginUserStepOneCoroutine(mobileNumber: String) {
        // To be implemented api call.


    }


}