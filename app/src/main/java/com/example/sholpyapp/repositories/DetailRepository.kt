package com.example.sholpyapp.repositories

import com.example.sholpyapp.base.BaseRepository
import com.example.sholpyapp.network.ProductDao
import com.example.sholpyapp.database.CartDao
import com.example.sholpyapp.database.WishlistDao
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem
import com.example.sholpyapp.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepository @Inject constructor(
    val productDao: ProductDao,
    val cartDao: CartDao,
    val wishlistDao: WishlistDao,
) :BaseRepository() {

     suspend fun getProductById(id: Int) : Resource<AllProductsResponseItem> = safeApiRequest {
         productDao.getProductById(id)
     }


    suspend fun addToCart(product:CartItem) = safeApiRequest{
        withContext(Dispatchers.IO){
            val isProductExists = checkProductData(product.id)
            if (!isProductExists) {
                cartDao.addProductCart(product)
            }
        }
    }

    suspend fun addToWishlist(product: WishlistItem)=safeApiRequest{
        withContext(Dispatchers.IO){
            wishlistDao.addProductWishlist(product)
        }
    }

    suspend fun deleteFromWishlist(product: WishlistItem)=safeApiRequest{
        withContext(Dispatchers.IO){
            wishlistDao.deleteProductWishlist(product)
        }
    }

    suspend fun checkProductData(id:Int):Boolean{
        return withContext(Dispatchers.IO) {
            val list = wishlistDao.getProductById(id)
            list.isNotEmpty()
        }
    }
}