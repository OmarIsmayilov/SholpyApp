package com.example.sholpyapp.base

import com.example.sholpyapp.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository{
    suspend fun <T : Any> safeApiRequest(apiRequest: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiRequest.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception.toString())
        }
    }

}
