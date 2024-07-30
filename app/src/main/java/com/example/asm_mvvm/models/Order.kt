package com.example.asm_mvvm.models

data class Order(
    val id: String,
    val productId: String,
    val productName: String,
    val image: String,
    val quantity: Int,
    val price: Double,
    val state: String,
    val account:String,
    val createdAt:String? = ""
)
