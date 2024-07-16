package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

data class CartRequest (
    @SerializedName("_id") var id: String? = null,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val image: String,
    val price: Double,
    val account:String
)