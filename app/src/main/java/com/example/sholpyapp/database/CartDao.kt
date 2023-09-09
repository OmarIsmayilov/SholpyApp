package com.example.sholpyapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem

@Dao
interface CartDao {

    @Insert
    suspend fun addProductCart(product: CartItem)

    @Delete
    suspend fun deleteProductCart(product: CartItem)

    @Update
    suspend fun updateProductCart(product: CartItem)

    @Query("select * from cart_table")
    suspend fun getAllProductCart(): List<CartItem>

    @Query("Delete from cart_table")
    suspend fun deleteAllProductCart()

}