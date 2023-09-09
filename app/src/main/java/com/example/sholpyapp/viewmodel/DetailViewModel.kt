package com.example.sholpyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.network.Resource
import com.example.sholpyapp.repositories.DetailRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
) : ViewModel() {

    private val _productData = MutableLiveData<AllProductsResponseItem?>()
    val productData: LiveData<AllProductsResponseItem?> get() = _productData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading



    fun getProductById(id: Int) {
        viewModelScope.launch {
            _loading.postValue(true)
            when (val request = repository.getProductById(id)) {
                is Resource.Success -> {
                    _productData.postValue(request.result)
                    _loading.postValue(false)
                }

                is Resource.Error -> {
                    _loading.postValue(false)
                }
            }


        }
    }

    fun addToCart(product: CartItem, count: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            try {
                repository.addToCart(product)
                _loading.postValue(false)
            }catch (e:Exception){

            }

        }
    }

    fun addToWishlist(product: WishlistItem) {
        _loading.postValue(true)
        viewModelScope.launch {
            repository.addToWishlist(product)
            _loading.postValue(false)
        }
    }

    fun deleteFromWishlist(product: WishlistItem) {
        _loading.postValue(true)
        viewModelScope.launch {
            repository.deleteFromWishlist(product)
            _loading.postValue(false)
        }
    }

}