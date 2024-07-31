package com.example.asm_mvvm.request

import com.google.gson.annotations.SerializedName

data class UserRequest (
    @SerializedName("_id") var id: String? = null,
    val username: String,
    val password: String,
    val avatar: String,
    val name: String,
)
