package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

class CartRequest (
    @SerializedName("_id") var id: String? = null,
    val productId: String,
    val quantity: Int,
)