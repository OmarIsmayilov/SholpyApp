package com.example.sholpyapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class AllProductsResponseItem(
    @SerializedName("category")
    val category: String?=null,
    @SerializedName("description")
    val description: String?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("image")
    val image: String?=null,
    @SerializedName("price")
    var price: Double?=null,
    @SerializedName("rating")
    val rating: Rating?=null,
    @SerializedName("title")
    val title: String?=null,
    val quantity : Int =1,
) : Parcelable