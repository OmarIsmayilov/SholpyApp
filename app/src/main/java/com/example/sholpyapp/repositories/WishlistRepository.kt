package com.example.sholpyapp.repositories

import com.example.sholpyapp.database.CartDao
import com.example.sholpyapp.database.WishlistDao
import com.example.sholpyapp.model.WishlistItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistRepository @Inject constructor(val dao: WishlistDao) {

    suspend fun getWishlistData() : List<WishlistItem>{
        return withContext(Dispatchers.IO){
            dao.getAllProductWishlist()
        }
    }

    suspend fun deleteWishlistData(){
        return withContext(Dispatchers.IO){
            dao.deleteAllProductWishlist()
        }
    }

}