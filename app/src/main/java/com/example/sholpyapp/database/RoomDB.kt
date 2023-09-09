package com.example.sholpyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sholpyapp.model.AllProductsResponseItem
import com.example.sholpyapp.model.CartItem
import com.example.sholpyapp.model.WishlistItem

@Database(entities = [WishlistItem::class, CartItem::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCartDao(): CartDao
    abstract fun getWishlistDao(): WishlistDao
}