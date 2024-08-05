package com.example.asm_mvvm.models

data class Comment (
    val id : String,
    val productId : String,
    val content : String,
    val rate : Double,
    val account : String,
    val avatar : String
)