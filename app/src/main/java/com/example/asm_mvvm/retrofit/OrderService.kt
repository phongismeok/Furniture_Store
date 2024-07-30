package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.NotificationRequest
import com.example.asm_mvvm.request.OrderRequest
import com.example.asm_mvvm.response.NotificationResponse
import com.example.asm_mvvm.response.OrderResponse
import okhttp3.ResponseBody
import retrofit2.Call
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

interface OrderService {
    @GET("get-list-order-by-account/{account}")
    suspend fun getOrderByAccount(@Path("account") account: String): Response<List<OrderResponse>>

    @GET("get-list-order-by-state/{account}/{state}")
    suspend fun getOrderByState(
        @Path("account") account: String,
        @Path("state") state: String
    ): Response<List<OrderResponse>>

    @POST("add-order")
    suspend fun addOrder(@Body orderRequest: OrderRequest): Response<Void>

    @PUT("update-state-order")
    @FormUrlEncoded
    suspend fun updateStateOrder(
        @Query("id") id: String,
        @Field("state") state: String
    ): Response<Void>

    @DELETE("delete-order/{id}")
    suspend fun deleteOrder(@Path("id") id: String): Response<ResponseBody>
}