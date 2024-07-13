package com.example.asm_mvvm.models


data class Product(
    val id: String,
    val productId: String,
    val productName: String,
    val price: Double,
    val describe: String,
    val image1: String,
    val image2: String,
    val image3: String,
    val type: Int,
    val stateFavorites: Int,
    val typeProduct: String,
)