package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.AllCategoryResponse
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.data.LoginUserResponse
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DashboardViewModel(): ViewModel(){

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _homeResponse = MutableLiveData<HomeScreenResponse>()
    val homeScreenResponse :LiveData<HomeScreenResponse> =_homeResponse

    private val _categoryResponse = MutableLiveData<AllCategoryResponse>()
    val categoryResponse :LiveData<AllCategoryResponse> = _categoryResponse


    val mainRepository = MainRepository()

    fun getAllCategory() = viewModelScope.launch {
        getAllCategoryCoroutine()


    }

    private suspend fun getAllCategoryCoroutine() {
        try {

            val response = mainRepository.getAllCategories()
            _isLoading.value = true
            response.enqueue(object : Callback<AllCategoryResponse> {

                override fun onResponse(
                    call: Call<AllCategoryResponse>,
                    response: Response<AllCategoryResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _categoryResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<AllCategoryResponse>, t: Throwable) {
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


    fun getHomeScreenResponse() = viewModelScope.launch {
        getHomeScreenResponseCoroutine()

    }

    private suspend fun getHomeScreenResponseCoroutine() {
        try {

            val response = mainRepository.getHomeScreenData()
            _isLoading.value = true
            response.enqueue(object : Callback<HomeScreenResponse> {

                override fun onResponse(
                    call: Call<HomeScreenResponse>,
                    response: Response<HomeScreenResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _homeResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<HomeScreenResponse>, t: Throwable) {
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