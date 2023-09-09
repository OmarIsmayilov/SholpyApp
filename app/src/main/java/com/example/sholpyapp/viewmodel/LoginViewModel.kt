package com.example.sholpyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sholpyapp.model.response.ResponseModel
import com.example.sholpyapp.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: AuthRepository) : ViewModel() {

    private val _resultAuth = MutableLiveData<ResponseModel?>()
    val resultAuth: LiveData<ResponseModel?>
        get() = _resultAuth

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun signIn(email: String, password: String){
        _loading.postValue(true)
        viewModelScope.launch {
            val result = repository.loginUser(email, password)
            _resultAuth.postValue(result)
            _loading.postValue(false)

        }
    }

    fun signUp(email: String, password: String){
        _loading.postValue(true)
        viewModelScope.launch {
            val result = repository.createUser(email, password)
            _resultAuth.postValue(result)
            _loading.postValue(false)
        }

    }

    fun signOutUser(){
        viewModelScope.launch {
            repository.exitUser()
        }
    }
}