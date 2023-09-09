package com.example.sholpyapp.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val homeRepository: HomeRepository,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _ratedProductData = MutableLiveData<AllProductsResponse?>()
    val ratedProductData: LiveData<AllProductsResponse?> get() = _ratedProductData

    private val _allProductData = MutableLiveData<AllProductsResponse?>()
    val allProductData: LiveData<AllProductsResponse?> get() = _allProductData

    private val _userPhoto = MutableLiveData<String?>()
    val userPhoto: LiveData<String?> get() = _userPhoto

    private val _theme = MutableLiveData<Boolean>()
    val theme: LiveData<Boolean> get() = _theme


    init {
        getProductData()
        getUserData()
    }


    private fun getProductData() {
        _loading.postValue(true)
        viewModelScope.launch {
            _ratedProductData.postValue(homeRepository.getRatedProducts())
            _allProductData.postValue(homeRepository.getAllProducts())
            _loading.postValue(false)
        }
    }


    private fun getUserData() {
        viewModelScope.launch {
            _userPhoto.postValue(homeRepository.getUserData())
        }
    }


}



