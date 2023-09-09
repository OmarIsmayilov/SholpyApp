package com.example.sholpyapp.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class AllProductsResponseItem(
    @SerializedName("id")
    var id: Int,
    @SerializedName("category")
    var category: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("price")
    var price: Double?,
    @SerializedName("rating")
    var rating: Rating,
    @SerializedName("title")
    var title: String?,
    var quantity : Int ,
) : Parcelable {}