package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.request.NotificationRequest
import com.example.asm_mvvm.response.NotificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {
    @GET("get-list-notification-by-account/{account}")
    suspend fun getNotificationByAccount(@Path("account") account: String): Response<List<NotificationResponse>>

    @POST("add-notification")
    suspend fun addNotification(@Body notificationRequest: NotificationRequest): Response<Void>
}