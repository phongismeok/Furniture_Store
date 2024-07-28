package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.ShippingRequest
import com.example.asm_mvvm.response.ProductResponse
import com.example.asm_mvvm.response.ShippingResponse
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

interface ShippingService {
    @GET("get-list-ships")
    suspend fun getListShip(): Response<List<ShippingResponse>>

    @GET("get-list-ship-by-account/{account}")
    suspend fun getShippingByAccount(@Path("account") account: String): Response<List<ShippingResponse>>

    @GET("get-ship-by-select/{account}/{select}")
    suspend fun getShippingBySelect(
        @Path("account") account: String,
        @Path("select") select: Int
    ): Response<ShippingResponse>

    @PUT("update-select-shipping")
    @FormUrlEncoded
    suspend fun updateSelectShipping(
        @Query("_id") id: String,
        @Field("select") select: Int
    ): Response<Void>

    @POST("add-shipping")
    suspend fun addShipping(@Body shippingRequest: ShippingRequest): Response<Void>

    @DELETE("delete-ship/{id}")
    suspend fun deleteShipping(@Path("id") id: String): Response<ResponseBody>
}