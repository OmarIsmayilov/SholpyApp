package com.example.sholpyapp.network

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val result: T) : Resource<T>()
    data class Error(val exception: String) : Resource<Nothing>()
}