package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductService {
    @GET("get-list-products")
    suspend fun getListPro(): Response<List<ProductResponse>>

    @GET("get-list-products-by-type/{type}")
    suspend fun getProductsByType(@Path("type") type: Int): Response<List<ProductResponse>>

    @GET("get-list-products-by-typeProduct/{typeProduct}")
    suspend fun getProductsByTypeProduct(@Path("typeProduct") typeProduct: String): Response<List<ProductResponse>>

    @GET("get-products-by-id")
    suspend fun getProductById(@Query("_id") id: String): ProductResponse

    @GET("search-product")
    suspend fun searchProduct(@Query("key") key: String): Response<List<ProductResponse>>

}

