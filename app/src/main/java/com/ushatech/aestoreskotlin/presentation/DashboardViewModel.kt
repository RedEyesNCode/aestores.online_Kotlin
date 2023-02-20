package com.ushatech.aestoreskotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ushatech.aestoreskotlin.data.HomeScreenResponse
import com.ushatech.aestoreskotlin.domain.MainRepository
import kotlinx.coroutines.launch

class DashboardViewModel(): ViewModel(){

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _homeResponse = MutableLiveData<HomeScreenResponse>()
    val homeScreenResponse :LiveData<HomeScreenResponse> =_homeResponse


    val mainRepository = MainRepository()


    fun getHomeScreenResponse() = viewModelScope.launch {  }




}