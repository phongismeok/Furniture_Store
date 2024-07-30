package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("_id") var id: String? = null,
    val productId: String,
    val productName: String,
    val image: String,
    val quantity: Int,
    val price: Double,
    val state: String,
    val account:String
)
