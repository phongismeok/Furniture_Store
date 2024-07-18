package com.example.asm_mvvm.models

data class Notification (
    val id: String,
    val title: String,
    val content: String,
    val state: Int,
    val image: String,
    val account:String
)