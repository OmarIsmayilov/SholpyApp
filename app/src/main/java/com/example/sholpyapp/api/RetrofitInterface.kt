package com.example.sholpyapp.api

import com.example.sholpyapp.model.AllProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {


    @GET("products")
    fun getAllProducts(
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
    ) : Call<AllProductsResponse>

    @GET("products")
    fun getRatedProducts(
        @Query("limit") limit: Int,
        @Query("sort") sort: String = "desc",
    ) : Call<AllProductsResponse>

    @GET("products/category/{category_name}")
    fun getProductbyCategory(
        @Path("category_name") category :String
    ) : Call<AllProductsResponse>

    @GET("products/category/{category_name}")
    fun getFilteredProducts(
        @Path("category_name") category :String,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
    ) : Call<AllProductsResponse>



}