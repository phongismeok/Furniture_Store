package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

data class ShippingRequest (
    @SerializedName("_id") var id: String? = null,
    val name: String,
    val address: String,
    val account: String,
    val select: Int
)