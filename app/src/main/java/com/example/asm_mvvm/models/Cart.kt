package com.example.asm_mvvm.models

data class Cart(
    val id: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val image: String,
    val price: Double,
    val account:String
)