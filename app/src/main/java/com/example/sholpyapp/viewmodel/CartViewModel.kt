package com.example.sholpyapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.network.Resource
import com.example.sholpyapp.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val repository: CartRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _cartData = MutableLiveData<List<CartItem>>()
    val cartData: LiveData<List<CartItem>> get() = _cartData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _total = MutableLiveData(0.0)
    val total: LiveData<Double> get() = _total

    init {
        viewModelScope.launch {
            getAllCartData()
        }
    }

    suspend fun getAllCartData() {
        _loading.postValue(true)
        when(val response = repository.getCartData()) {
           is Resource.Success -> {
               Log.e(TAG, "viewmodel result: ${response.result}", )
               _cartData.value = response.result
               getTotal(response.result)
               _loading.value = false
           }
           is Resource.Error -> {
               _error.value = response.exception
               _loading.postValue(false)
           }
       }

    }

    fun deleteAllData() {
        _loading.postValue(true)
        viewModelScope.launch {
            repository.deleteAllCartData()
            getAllCartData()
            _total.postValue(0.0)
            _loading.postValue(false)
        }
    }

    private fun getTotal(list: List<CartItem>) {
        list.forEach { pr ->
            increase(pr.price)
        }



    }


    fun decrease(price: Double) {
        _total.value = _total.value?.minus(price)

    }

    fun increase(price: Double) {
        _total.value = _total.value?.plus(price)
    }

}