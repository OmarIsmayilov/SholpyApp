package com.example.sholpyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    @ColumnInfo(name = "category") var category: String ,
    @ColumnInfo(name = "description") var description: String ,
    @ColumnInfo(name = "image") var image: String ,
    @ColumnInfo(name = "price") var price: Double ,
    @ColumnInfo(name = "title") var title: String ,
    @ColumnInfo(name = "quantity") var quantity: Int ,

    )

