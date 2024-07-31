package com.example.asm_mvvm.retrofit

import com.example.asm_mvvm.request.PushNotificationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PushNotificationService {
    @POST("send-notification")
    suspend fun sendNotification(@Body pushNotificationRequest: PushNotificationRequest): Response<Void>
}