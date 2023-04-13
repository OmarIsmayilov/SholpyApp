package com.example.sholpyapp.api

import com.example.sholpyapp.utils.Constants.BASE_URL
import retrofit2.create

class ApiUtils {
    companion object{
        fun getApi() : RetrofitInterface{
            return RetrofitClient.getClient(BASE_URL).create(RetrofitInterface::class.java)
        }
    }
}