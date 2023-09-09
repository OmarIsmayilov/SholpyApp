package com.example.sholpyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sholpyapp.database.WishlistDao
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.repositories.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor( val repository: WishlistRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _wishlistData = MutableLiveData<List<WishlistItem>>()
    val wishlistData: LiveData<List<WishlistItem>> get() = _wishlistData

    init {
        getWishlistData()
    }

    fun getWishlistData(){
        _loading.postValue(true)
        viewModelScope.launch {
            _wishlistData.postValue(repository.getWishlistData())
            _loading.postValue(false)
        }
    }

    fun deleteAllData(){
        _loading.postValue(true)
        viewModelScope.launch {
            repository.deleteWishlistData()
            getWishlistData()
            _loading.postValue(false)
        }
    }

}