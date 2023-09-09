package com.example.sholpyapp.repositories

import com.example.sholpyapp.database.CartDao
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepository @Inject constructor(private val dao: CartDao) {

    suspend fun getCartData(): Resource<List<CartItem>> {
        return try {
            Resource.Success(dao.getAllProductCart())
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }


    suspend fun deleteAllCartData() {
        return withContext(Dispatchers.IO) {
            dao.deleteAllProductCart()
        }
    }


}