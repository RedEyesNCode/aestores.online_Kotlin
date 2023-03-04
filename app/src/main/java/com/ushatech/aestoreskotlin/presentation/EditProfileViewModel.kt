package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.CommonResponseData
import com.ushatech.aestoreskotlin.data.SelectionResponseData
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EditProfileViewModel():ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading
    val mainRepository = MainRepository()


    private val _countryResponse = MutableLiveData<SelectionResponseData>()
    val countryResponse :LiveData<SelectionResponseData> = _countryResponse

    private val _stateResponse = MutableLiveData<SelectionResponseData>()
    val stateResponse :LiveData<SelectionResponseData> = _stateResponse

    private val _cityResponse = MutableLiveData<SelectionResponseData>()
    val cityResponse :LiveData<SelectionResponseData> = _cityResponse


    fun getCities(stateId:String) = viewModelScope.launch {

        getCitiesCoroutine(stateId)

    }

    private suspend fun getCitiesCoroutine(stateId: String) {
        try {

            val response = mainRepository.getCities(stateId)
            _isLoading.value = true
            response.enqueue(object : Callback<SelectionResponseData> {

                override fun onResponse(
                    call: Call<SelectionResponseData>,
                    response: Response<SelectionResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _cityResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<SelectionResponseData>, t: Throwable) {
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


    fun getStates(countryId:String) = viewModelScope.launch {

        getStatesCoroutine(countryId)

    }

    private suspend fun getStatesCoroutine(countryId: String) {
        try {

            val response = mainRepository.getStates(countryId)
            _isLoading.value = true
            response.enqueue(object : Callback<SelectionResponseData> {

                override fun onResponse(
                    call: Call<SelectionResponseData>,
                    response: Response<SelectionResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _stateResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<SelectionResponseData>, t: Throwable) {
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

    fun getCountry() = viewModelScope.launch {
        getCountriesCoroutine()


    }

    private suspend fun getCountriesCoroutine() {
        try {

            val response = mainRepository.getCountries()
            _isLoading.value = true
            response.enqueue(object : Callback<SelectionResponseData> {

                override fun onResponse(
                    call: Call<SelectionResponseData>,
                    response: Response<SelectionResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _countryResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<SelectionResponseData>, t: Throwable) {
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