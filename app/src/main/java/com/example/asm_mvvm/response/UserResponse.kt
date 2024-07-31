package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Favorites
import com.example.asm_mvvm.models.User
import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("name") val name: String,
){
    fun toUser(): User {
        return User(
            id = this.id,
            username = this.username,
            password = this.password,
            avatar = this.avatar,
            name = this.name,
        )
    }
}