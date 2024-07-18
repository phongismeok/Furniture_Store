package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

data class NotificationRequest (
    @SerializedName("_id") var id: String? = null,
    val title: String,
    val content: String,
    val state: Int,
    val image: String,
    val account:String
)