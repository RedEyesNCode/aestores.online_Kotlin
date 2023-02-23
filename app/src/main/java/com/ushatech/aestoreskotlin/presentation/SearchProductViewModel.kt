package com.ushatech.aestoreskotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.SearchProductResponse
import com.ushatech.aestoreskotlin.domain.MainRepository
import com.ushatech.aestoreskotlin.session.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchProductViewModel():ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _searchProductsResponse = MutableLiveData<SearchProductResponse>()
    val searchProductResponse:LiveData<SearchProductResponse> = _searchProductsResponse


    val mainRepository = MainRepository()


    fun searchProduct(searchTerm:String, categoryId: Int?, subCategoryId: Int?, superCateogryId: Int?)= viewModelScope.launch {

        searchProductCoroutine(searchTerm,categoryId,subCategoryId,superCateogryId)

    }

    private suspend fun searchProductCoroutine(
        searchTerm: String,
        categoryId: Int?,
        subCategoryId: Int?,
        superCateogryId: Int?
    ) {

        try {

            val response = mainRepository.searchProduct(searchTerm,categoryId,subCategoryId,superCateogryId,1)
            _isLoading.value = true
            response.enqueue(object : Callback<SearchProductResponse> {

                override fun onResponse(
                    call: Call<SearchProductResponse>,
                    response: Response<SearchProductResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _searchProductsResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {

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