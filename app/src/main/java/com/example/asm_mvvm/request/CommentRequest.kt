package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @SerializedName("_id") var id: String? = null,
    val productId: String,
    val content: String,
    val rate: Double,
    val account: String,
    val avatar : String
)
