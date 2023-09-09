package com.example.sholpyapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.WishlistItem

@Dao
interface WishlistDao {

    @Insert
    suspend fun addProductWishlist(product:WishlistItem)

    @Delete
    suspend fun deleteProductWishlist(product: WishlistItem)

    @Query("select * from wishlist_table")
    suspend fun getAllProductWishlist() : List<WishlistItem>

    @Query("SELECT * FROM wishlist_table WHERE id = :productId")
    suspend fun getProductById(productId: Int): List<WishlistItem>

    @Query("Delete from wishlist_table")
    suspend fun deleteAllProductWishlist()


}