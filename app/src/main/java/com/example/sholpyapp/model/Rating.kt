package com.example.sholpyapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @SerializedName("count")
    val count: Int?=null,
    @SerializedName("rate")
    val rate: Double?=null
):Parcelable