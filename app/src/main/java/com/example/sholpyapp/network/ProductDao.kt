package com.example.sholpyapp.network

import com.example.sholpyapp.model.AllProductsResponse
import com.example.sholpyapp.model.AllProductsResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDao {


    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
    ) : Response<AllProductsResponse>

    @GET("products")
    suspend fun getRatedProducts(
        @Query("limit") limit: Int,
        @Query("sort") sort: String = "desc",
    ) : Response<AllProductsResponse>

    @GET("products/category/{category_name}")
    suspend fun getProductbyCategory(
        @Path("category_name") category :String
    ) : Response<AllProductsResponse>

    @GET("products/category/{category_name}")
    suspend fun getFilteredProducts(
        @Path("category_name") category :String,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
    ) : Response<AllProductsResponse>


    @GET("products/{product_id}")
    suspend fun getProductById(
        @Path("product_id") id: Int,
    ) : AllProductsResponseItem

}