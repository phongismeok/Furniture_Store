package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.response.CartResponse
import com.example.asm_mvvm.response.FavoritesResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CartService {

    @GET("get-list-cart-by-account/{account}")
    suspend fun getCartByAccount(@Path("account") account: String): Response<List<CartResponse>>

    @GET("get-cart-by-productId/{account}/{productId}")
    suspend fun getCartByProductId(
        @Path("account") account: String,
        @Path("productId") productId: String
    ): Response<CartResponse>

    @POST("add-product-to-cart")
    suspend fun addProductToCart(@Body cartRequest: CartRequest): Response<Void>

    @PUT("update-quantity-cart")
    @FormUrlEncoded
    suspend fun updateQuantityCart(
        @Query("id") id: String,
        @Field("quantity") quantity: Int
    ): Response<Void>

    @DELETE("delete-cart/{id}")
    suspend fun deleteCart(@Path("id") id: String): Response<ResponseBody>
}