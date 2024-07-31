package com.example.asm_mvvm.request

data class PushNotificationRequest(
    val token : String,
    val title : String,
    val body : String
)
